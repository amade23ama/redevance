package sn.dscom.backend.batchs;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.multipart.MultipartFile;
import sn.dscom.backend.common.dto.ChargementDTO;
import sn.dscom.backend.common.dto.DepotDTO;
import sn.dscom.backend.common.dto.DepotDcsomDTO;
import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Configuration
public class BatchConfiguration {
    private static final Logger log= LoggerFactory.getLogger(BatchConfiguration.class);
    private static final ThreadLocal<MultipartFile> batchFileThreadLocal = new ThreadLocal<>();
    public static void setBatchFile(MultipartFile file) {
        batchFileThreadLocal.set(file);
    }
    public static MultipartFile getBatchFile() {
        return batchFileThreadLocal.get();
    }
    public static DepotDTO getDepotDTO() {
        return depot;
    }
    private static Map<String, String> mapInverse;
    private static Environment environment;
    private static DepotDTO depot;
    public static void processEnvironment(Environment environment) {
        BatchConfiguration.environment = environment;
    }
    public static void processMapInverse(Map<String, String> map) {
        BatchConfiguration.mapInverse = map;
    }

    public static void processDepot(DepotDTO depot) {
        BatchConfiguration.depot=depot;
    }

    @Bean
    @StepScope
    public ItemReader<List<DepotDcsomDTO>> myItemReader() {
        MultipartFile batchFile = getBatchFile();
        return new FileListItemReader(batchFile, mapInverse, environment);
    }

    @Bean
    public ImportItemProcessor myItemProcessor() {
        return new ImportItemProcessor();
    }


    @Bean
    @StepScope
    public ImportItemWriter myItemWriter() {
        return new ImportItemWriter(depot);

    }

    @Bean
    public Step sampleStep(JobRepository jobRepository,PlatformTransactionManager transactionManager

    ) {
        return new StepBuilder("sampleStep")

                .repository(jobRepository)
                .<List<DepotDcsomDTO>,ImportProcessingDTO>chunk(10,transactionManager)
                .reader(myItemReader() )
                .processor(myItemProcessor())
                .writer(myItemWriter())
                //.faultTolerant()
                //.skipPolicy(new CommonMetierExceptionSkipper())
                //.skipLimit(10)
                //.listener(new MySkipListener())
                .listener(myStepListener())
                .build();
    }

    @Bean
    public Job myJob(JobRepository jobRepository, Step sampleStep) {
        return new JobBuilder("myJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(sampleStep)
                .listener(jobCompletionListener())
                .build();
    }

    @Bean
    public Step taskletStep(JobRepository jobRepository,PlatformTransactionManager transactionManager) {
        return new StepBuilder("first step", jobRepository)
                .tasklet((stepContribution, chunkContext) -> {
                    log.info("This is first tasklet step");
                    log.info("SEC = {}", chunkContext.getStepContext().getStepExecutionContext());
                    return RepeatStatus.FINISHED;
                }, transactionManager).build();
    }
    @Bean
    public JobRepository myJobRepository(DataSource dataSource, PlatformTransactionManager transactionManager) throws Exception {
        JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
        factory.setDataSource(dataSource);
        factory.setTransactionManager(transactionManager);
        factory.setIsolationLevelForCreate("ISOLATION_READ_COMMITTED");
        factory.afterPropertiesSet();
        return factory.getObject();
    }

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(5);
        taskExecutor.setMaxPoolSize(10);
        taskExecutor.setQueueCapacity(25);
        return taskExecutor;
    }
    @Bean
    public JobCompletionListener jobCompletionListener() {
        return new JobCompletionListener();
    }
    @Bean
    public TaskExecutor asyncTaskExecutor() {
        return new SimpleAsyncTaskExecutor();
    }
    @Bean
    public MyStepListener myStepListener() {
        return new MyStepListener();
    }
}
