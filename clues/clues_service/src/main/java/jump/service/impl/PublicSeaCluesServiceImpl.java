package jump.service.impl;

import com.github.pagehelper.PageHelper;
import jump.dao.ICluesDao;
import jump.dao.IPublicSeaCluesDao;
import jump.domain.PublicSea;
import jump.service.IPublicSeaCluesService;
import jump.utils.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PublicSeaCluesServiceImpl implements IPublicSeaCluesService {

    @Autowired
    private IPublicSeaCluesDao publicSeaCluesDao;

    @Autowired
    private IPublicSeaCluesService publicSeaCluesService;

    @Autowired
    private ICluesDao cluesDao;

    @Override
    public List<PublicSea> findAll(Integer page, Integer size) throws Exception{
        //参数page是页码值, 参数page代表是每页显示条数
        PageHelper.startPage(page, size);
        return publicSeaCluesDao.findAll();
    }

    @Override
    public void insert(PublicSea publicSea) throws Exception{
        publicSeaCluesDao.insert(publicSea);
    }

    @Override
    public void updateById(PublicSea publicSea) throws Exception{
        publicSeaCluesDao.updateById(publicSea);
    }

    @Override
    public PublicSea selectPublicSeaById(int publicSeaId) throws Exception {
        return publicSeaCluesDao.selectPublicSeaById(publicSeaId);
    }

    @Override
    public List<String> selectTrackNumberByclientName(String clientName) throws Exception {
        return publicSeaCluesDao.selectTrackNumberByclientName(clientName);
    }

    @Override
    public List<String> selectCNumber() throws Exception {
        List<String> list = cluesDao.selectCNumber();
        List<String> list1 = publicSeaCluesDao.selectCNumber();
        list1.addAll(list);
        return list1;
    }

    @Override
    public void addPublicSeaToPublicSea(PublicSea publicSea) throws Exception {
        //生成线索号
        String randomString = null;
        List<String> list = publicSeaCluesService.selectCNumber();
        while(true){
            randomString = RandomString.randomString("CH",7);
            if(!list.contains(randomString)){
                break;
            }
        }
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(user.getUsername());
        publicSea.setEntryNumber(user.getUsername());
        publicSea.setcNumber(randomString);
        publicSea.setLastTime(new Date());
        publicSea.setEntryTime(new Date());
        publicSeaCluesDao.save(publicSea);
    }

    @Override
    public List<PublicSea> findPublicSeaByNameAndMobile(String clientName, String mobile) throws Exception {
        return publicSeaCluesDao.findPublicSeaByNameAndMobile(clientName, mobile);
    }
}
