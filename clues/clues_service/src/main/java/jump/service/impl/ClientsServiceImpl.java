package jump.service.impl;

import com.github.pagehelper.PageHelper;
import jump.dao.IClientsDao;
import jump.domain.Clients;
import jump.domain.Clues;
import jump.service.IClientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.lang.ref.SoftReference;
import java.util.Date;
import java.util.List;

@Service
public class ClientsServiceImpl implements IClientsService {

    @Autowired
    private IClientsDao clientsDao;

    @Override
    public List<Clients> findAll(int page, int size) throws Exception {
        //参数page是页码值, 参数page代表是每页显示条数
        PageHelper.startPage(page, size);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return clientsDao.findAll(user.getUsername());
    }

    @Override
    public void insert(Clients client) throws Exception {
        client.setLastTime(new Date());
        client.setTransformTime(new Date());
        clientsDao.insert(client);
    }

    @Override
    public Clients selectClientsByNumber(String cNumber) throws Exception{
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return clientsDao.findByNumber(cNumber, user.getUsername());
    }

    @Override
    public Clients selectClientsById(int id) throws Exception{
        return clientsDao.findById(id);
    }

    @Override
    public void updateById(Clients clients) throws Exception{
        clientsDao.updateById(clients);
    }

    @Override
    public void ClientsTransform(Clues clues) {

    }

    @Override
    public Clients selectClientsByNumberAndTrackNumber(String cNumber, String userNumber) throws Exception {
        return clientsDao.selectClientsByNumber(cNumber,userNumber);
    }
}
