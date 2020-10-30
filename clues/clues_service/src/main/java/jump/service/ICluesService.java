package jump.service;

import jump.domain.Clues;

import java.io.File;
import java.util.List;

public interface ICluesService {
    List<Clues> findAll(int page, int size) throws Exception;

    List<Clues> findAllByUsername(int page, int size, String username) throws Exception;

    void addClueToClues(Clues clues) throws Exception;

    void changeById(Clues clues) throws Exception;

    Clues selectCluesById(int cluesId) throws Exception;

    void updateCluesTransform(Clues clues)throws Exception;

    List<String> selectCNumber() throws Exception;

    List<Clues> findByCluesNameAndMobile(String name, String mobile) throws Exception;

}
