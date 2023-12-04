package sn.dscom.backend.batchs;

import com.opencsv.CSVReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;
import sn.dscom.backend.common.constants.Enum.ErreurEnum;
import sn.dscom.backend.common.dto.DepotDcsomDTO;
import sn.dscom.backend.common.exception.CommonMetierException;
import sn.dscom.backend.service.converter.BuilderDepotDcsomConverter;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class FileListItemReader implements ItemReader<List<DepotDcsomDTO>> {
    private static final Logger log= LoggerFactory.getLogger(FileListItemReader.class);
    private CSVReader csvReader;
    Map<String, String> mapInverse ;
    private  Environment environment;
    private static final int THREAD_POOL_SIZE = 5;

    FileListItemReader(MultipartFile multipartFile, Map<String, String> mapInverse, Environment environment) {
        try {
            this.environment=environment;
            this.mapInverse=mapInverse;
            if (multipartFile != null && !multipartFile.isEmpty()) {
                this.csvReader = new CSVReader(new InputStreamReader(multipartFile.getInputStream()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public List<DepotDcsomDTO> read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        List<DepotDcsomDTO> items = new ArrayList<>();
        List<DepotDcsomDTO> listDepotDcsomDTO = new ArrayList<>();
        if (csvReader != null){
        try {
            List<String>  header = tabToList(csvReader.readNext());
            List<String[]> csvData = csvReader.readAll();
            BuilderDepotDcsomConverter builderDepotDcsomConverter =new BuilderDepotDcsomConverter();
            listDepotDcsomDTO= builderDepotDcsomConverter.buildDepotDcsomDTOFromFichier(csvData, header, mapInverse,environment);

        } catch (IOException e) {
           // e.printStackTrace();
            throw new CommonMetierException(HttpStatus.NOT_FOUND.value(), ErreurEnum.ERR_NOT_FOUND);

        }
        }
        return listDepotDcsomDTO.isEmpty() ? null : listDepotDcsomDTO;
    }
    private static List<String> tabToList(String[] nextLine) {
        if (nextLine != null) {
            return Arrays.stream(nextLine)
                    .flatMap(line -> Arrays.stream(line.split(";")))
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

}
