package org.cruk.clarity.api.cache.spring;

import org.cruk.clarity.api.cache.ClarityAPICache;
import org.cruk.clarity.api.cache.EhCacheManagerFactory;
import org.cruk.clarity.api.spring.ClarityClientConfiguration;
import org.ehcache.CacheManager;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
@Import(ClarityClientConfiguration.class)
@ComponentScan("org.cruk.clarity.api.cache")
public class ClarityClientCacheConfiguration
{
    public ClarityClientCacheConfiguration()
    {
    }

    @Bean
    public FactoryBean<CacheManager> clarityCacheManager()
    {
        ClassPathResource config = new ClassPathResource("ehcache.xml", ClarityAPICache.class);

        EhCacheManagerFactory factory = new EhCacheManagerFactory();
        factory.setConfigLocation(config);
        return factory;
    }
}
