package spring.batch.job;

import java.nio.file.FileSystems;
import java.nio.file.Paths;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.ExitCodeMapper;
import org.springframework.batch.core.launch.support.SimpleJvmExitCodeMapper;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import spring.batch.jdbc.DBOperations;

public class LaunchJob
{
	public static void main(String[] args)
	{
		String resourcesPath = Paths.get("").toAbsolutePath().toString();
		String fileSeparator = FileSystems.getDefault().getSeparator();

		String propertiesFile = resourcesPath + fileSeparator + "resources" + fileSeparator + "job.properties";
		String inputFile = resourcesPath + fileSeparator + "resources" + fileSeparator + "input.ldif";

		System.out.println(propertiesFile);
		System.out.println(inputFile);
		System.setProperty("property.location", propertiesFile);

		ExitCodeMapper exitMapper = new SimpleJvmExitCodeMapper();

		try (AbstractApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml"))
		{
			JobLauncher jobLauncher = (JobLauncher) applicationContext.getBean("jobLauncher");
			JobRepository jobRepository = (JobRepository) applicationContext.getBean("jobRepository");
			Job job = (Job) applicationContext.getBean("usersExtractJob");
			JobParametersBuilder builder = new JobParametersBuilder();
			builder.addString("input.file.name", inputFile);

			DBOperations dbOperations = (DBOperations) applicationContext.getBean("dbOperations");
			
			if (dbOperations.truncate())
			{
				jobLauncher.run(job, builder.toJobParameters());
			}
			else
			{
				System.out.println("Exception occurred - exiting job");
				System.exit(exitMapper.intValue(ExitStatus.FAILED.getExitCode()));
			}

			JobExecution jobExecution = jobRepository.getLastJobExecution(job.getName(), builder.toJobParameters());

			for (StepExecution stepExec : jobExecution.getStepExecutions())
			{
				System.out.println(stepExec.toString());
			}

			System.out.println(jobExecution.toString());
			System.exit(exitMapper.intValue(jobExecution.getExitStatus().getExitCode()));
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.exit(exitMapper.intValue(ExitStatus.FAILED.getExitCode()));
		}
	}
}