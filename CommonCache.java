package com.yiyunkj.yidongban.utils;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.yiyunkj.yidongban.enums.CommonCacheMethodEnum;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

/**
 * author:ZhengXing
 * datetime:2018/2/27 0027 16:58
 * 通用缓存
 */
@Slf4j
public class CommonCache<T> {


	/**
	 * @Description 创建缓存
	 * @Author ZhengXing
	 * @Param [cacheMethodEnum 缓存过期方法, expireTime 过期时间,秒, initialCapacity 缓存初始容量, maximumSize 缓存最大容量]
	 * @return
	 **/
	public CommonCache(CommonCacheMethodEnum cacheMethodEnum, int expireTime, int initialCapacity,int maximumSize) {
		Caffeine<Object, Object> caffeine = Caffeine.newBuilder()
				.initialCapacity(initialCapacity)
				.maximumSize(maximumSize);
		if (CommonCacheMethodEnum.AFTER_ACCESS.equals(cacheMethodEnum))
			caffeine.expireAfterAccess(expireTime, TimeUnit.SECONDS);
		else if (CommonCacheMethodEnum.AFTER_WRITE.equals(cacheMethodEnum))
			caffeine.expireAfterWrite(expireTime, TimeUnit.SECONDS);
		//传入缓存加载策略,key不存在时调用该方法返回一个value回去
		this.cache = caffeine.build(key -> null);
	}

	//创建缓存
	private final LoadingCache<String, T> cache;


	/**
	 * 获取数据
	 */
	public T get(String key) {
		return cache.getIfPresent(key);
	}

	/**
	 * 获取并删除
	 */
	public T getAndRemove(String key) {
		T result = cache.getIfPresent(key);
		if (result != null) {
			cache.invalidate(key);
		}
		return result;
	}

	/**
	 * 获取所有数据值
	 */
	public Collection<T> getValues() {
		return cache.asMap().values();
	}

	/**
	 * 获取所有key
	 */

	/**
	 * 存入数据
	 */
	public void put(String key, T obj) {
		cache.put(key, obj);
	}

	/**
	 * 删除数据
	 */
	public void remove(String key) {
		cache.invalidate(key);
	}

	/**
	 * 长度
	 * ps:该长度不是强一致性的.
	 */
	public long size() {

		return cache.estimatedSize();
	}

	/**
	 * 判断key是否存在
	 */
	public boolean containKey(String key){
		return cache.get(key) != null;
	}

	/**
	 * 判断值是否存在
	 */
	public boolean containValue(T value) {
		return cache.asMap().values().parallelStream().filter(item -> item.equals(value)).count() > 0;
	}

	/**
	 * 判断某个属性是否存在, 自行传入方法
	 */
	public  boolean containValue(Predicate<? super T> predicate) {
		return cache.asMap().values().parallelStream().filter(predicate).count() > 0;
	}

}
