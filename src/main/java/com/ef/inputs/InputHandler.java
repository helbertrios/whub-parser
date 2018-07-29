package com.ef.inputs;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Arrays;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.ef.log.ParserLogger;
import com.ef.repository.to.Duration;

public class InputHandler {

	private static final CommandLineParser PARSER = new DefaultParser();
	
	private Options options;


	public InputData extractParameters(final String[] arg) {

		ParserLogger.getLogger().fine("Extract input parameters.");

		final CommandLine line = this.getCommandLine(arg);

		final InputData inputData = new InputData();
		for (ParameterType pt : ParameterType.values()) {
			final Object obj = pt.convert(getParameter(line, pt));
			ParserLogger.getLogger().fine(pt.getDescription() + ": " +  obj.toString());
			
			if (ParameterType.START_DATE.equals(pt)) {
				inputData.setStartDate((LocalDateTime) obj);
			} else if (ParameterType.DURATION.equals(pt)) {
				inputData.setDuration((Duration) obj);
			} else if (ParameterType.THRESHOLD.equals(pt)) {
				inputData.setThreshold((Long) obj);
			} else if (ParameterType.FILEPATH.equals(pt)) {
				inputData.setFilePath((Path) obj);
			}			
		}
		
		ParserLogger.getLogger().fine("inputData: " +  inputData.toString());
		return inputData;

	}

	public String getParameter(final CommandLine line, final ParameterType parameterType) {
		final String[] result = line.getOptionValues(parameterType.getKey());
		ParserLogger.getLogger().fine(parameterType.getDescription() + ": " + Arrays.toString(result));
		if ( (result == null) || (result.length == 0)){
			return null;
		}
		return result[0];
	}

	private CommandLine getCommandLine(final String[] arguments) {
		try {
			return PARSER.parse(this.getOptions(), arguments);
		} catch (ParseException e) {
			throw new IllegalArgumentException("Something went wrong, try something like this: java -cp \"parser.jar\" com.ef.Parser --startDate=2017-01-01.13:00:00 --duration=hourly --threshold=100 --accesslog=/home/acess.log");
		}

	}

	private Options getOptions() {

		if (options == null) {
			options = new Options();
			for (ParameterType pt : ParameterType.values()) {
				options.addOption(pt.getKey(), pt.getLongKey(), true, pt.getDescription());
			}
		}

		return options;
	}
	

	
	
}
