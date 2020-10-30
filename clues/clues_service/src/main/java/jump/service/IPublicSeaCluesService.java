package jump.service;

import jump.domain.PublicSea;

import java.util.List;

public interface IPublicSeaCluesService {

    List<PublicSea> findAll(Integer page, Integer size) throws Exception;

    void insert(PublicSea publicSea) throws Exception;

    void updateById(PublicSea publicSea) throws Exception;

    PublicSea selectPublicSeaById(int publicSeaId) throws Exception;

    List<String> selectTrackNumberByclientName(String clientName) throws Exception;

    List<String> selectCNumber() throws Exception;

    void addPublicSeaToPublicSea(PublicSea publicSea) throws Exception;

    List<PublicSea> findPublicSeaByNameAndMobile(String clientName, String mobile) throws Exception;
}
