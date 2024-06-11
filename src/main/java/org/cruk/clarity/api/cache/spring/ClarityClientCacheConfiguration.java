package org.cruk.clarity.api.cache.spring;

import java.time.Duration;

import org.cruk.clarity.api.cache.internal.CacheElementWrapper;
import org.ehcache.CacheManager;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.EntryUnit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.genologics.ri.LimsEntity;
import com.genologics.ri.artifact.Artifact;
import com.genologics.ri.artifact.Demux;
import com.genologics.ri.automation.Automation;
import com.genologics.ri.container.Container;
import com.genologics.ri.containertype.ContainerType;
import com.genologics.ri.controltype.ControlType;
import com.genologics.ri.file.ClarityFile;
import com.genologics.ri.instrument.Instrument;
import com.genologics.ri.lab.Lab;
import com.genologics.ri.process.ClarityProcess;
import com.genologics.ri.processtype.ProcessType;
import com.genologics.ri.project.Project;
import com.genologics.ri.protocolconfiguration.Protocol;
import com.genologics.ri.reagenttype.ReagentType;
import com.genologics.ri.researcher.Researcher;
import com.genologics.ri.sample.Sample;
import com.genologics.ri.stage.Stage;
import com.genologics.ri.stepconfiguration.ProtocolStep;
import com.genologics.ri.workflowconfiguration.Workflow;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = false)
@ComponentScan("org.cruk.clarity.api.cache")
public class ClarityClientCacheConfiguration
{
    protected CacheConfiguration<String, CacheElementWrapper> smallMedium;
    protected CacheConfiguration<String, CacheElementWrapper> smallLong;
    protected CacheConfiguration<String, CacheElementWrapper> mediumShort;
    protected CacheConfiguration<String, CacheElementWrapper> mediumMedium;
    protected CacheConfiguration<String, CacheElementWrapper> largeShort;

    public ClarityClientCacheConfiguration()
    {
        smallMedium = cacheConfig(100, 10);
        smallLong = cacheConfig(100, 30);
        mediumShort = cacheConfig(250, 5);
        mediumMedium = cacheConfig(250, 10);
        largeShort = cacheConfig(1800, 5);
    }

    protected CacheConfiguration<String, CacheElementWrapper> cacheConfig(int number, int expiry)
    {
        var resourcePools = ResourcePoolsBuilder.newResourcePoolsBuilder().heap(number, EntryUnit.ENTRIES);

        return CacheConfigurationBuilder
                .newCacheConfigurationBuilder(String.class, CacheElementWrapper.class, resourcePools)
                .withExpiry(ExpiryPolicyBuilder.timeToIdleExpiration(Duration.ofMinutes(expiry)))
                .build();
    }

    @Bean // Calls close() on destroy implicitly.
    public CacheManager clarityCacheManager()
    {
        return CacheManagerBuilder.newCacheManagerBuilder()
            .withCache(LimsEntity.class.getName(), cacheConfig(500, 10))
            .withCache(Artifact.class.getName(), largeShort)
            .withCache(Demux.class.getName(), mediumShort)
            .withCache(Automation.class.getName(), smallLong)
            .withCache(Container.class.getName(), mediumShort)
            .withCache(ContainerType.class.getName(), smallLong)
            .withCache(ControlType.class.getName(), smallLong)
            .withCache(ClarityFile.class.getName(), largeShort)
            .withCache(Instrument.class.getName(), smallLong)
            .withCache(Lab.class.getName(), mediumMedium)
            .withCache(ClarityProcess.class.getName(), mediumShort)
            .withCache(ProcessType.class.getName(), smallLong)
            .withCache(Project.class.getName(), mediumShort)
            .withCache(Researcher.class.getName(), mediumMedium)
            .withCache(ReagentType.class.getName(), largeShort)
            .withCache(Sample.class.getName(), largeShort)
            .withCache(Stage.class.getName(), smallLong)
            .withCache(Protocol.class.getName(), smallLong)
            .withCache(ProtocolStep.class.getName(), smallLong)
            .withCache(Workflow.class.getName(), smallLong)
            .build(true);
    }
}
