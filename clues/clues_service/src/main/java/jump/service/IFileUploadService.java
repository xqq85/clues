package jump.service;

import java.io.File;

public interface IFileUploadService {
    public int importExcelClues(File file) throws Exception;

    int importExcelPublicSea(File file) throws Exception;
}
