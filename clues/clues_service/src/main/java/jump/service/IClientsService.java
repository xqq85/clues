package jump.service;

import jump.domain.Clients;
import jump.domain.Clues;

import java.util.List;

public interface IClientsService {
    List<Clients> findAll(int page, int size) throws Exception;

    void insert(Clients client) throws Exception;

    Clients selectClientsByNumber(String cNumber) throws Exception;

    Clients selectClientsById(int id) throws Exception;

    void updateById(Clients clients) throws Exception;

    void ClientsTransform(Clues clues);

    Clients selectClientsByNumberAndTrackNumber(String cNumber, String userNumber) throws Exception;
}
