package jump.service.impl;

import com.github.pagehelper.PageHelper;
import com.sun.org.apache.xml.internal.resolver.readers.ExtendedXMLCatalogReader;
import jump.dao.ICluesDao;
import jump.domain.Clues;
import jump.service.ICluesService;
import jump.utils.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CluesServiceImpl implements ICluesService {

    @Autowired
    private ICluesDao cluesDao;

    @Autowired
    private ICluesService cluesService;

    @Override
    public List<Clues> findAll(int page, int size) throws Exception{
        //参数page是页码值, 参数page代表是每页显示条数
        PageHelper.startPage(page, size);
        return cluesDao.findAll();
    }

    @Override
    public List<Clues> findAllByUsername(int page, int size, String username) throws Exception {
        //参数page是页码值, 参数page代表是每页显示条数
        PageHelper.startPage(page, size);
        return cluesDao.findAllByUsername(username);
    }

    @Override
    public void addClueToClues(Clues clues) throws Exception{
        //生成线索号
        String randomString = null;
        List<String> list = cluesService.selectCNumber();
        while(true){
            randomString = RandomString.randomString("CH",7);
            if(!list.contains(randomString)){
                break;
            }
        }
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(user.getUsername());
        clues.setEntryNumber(user.getUsername());
        clues.setcNumber(randomString);
        cluesDao.save(clues);
    }

    @Override
    public void changeById(Clues clues) throws Exception{
        cluesDao.changeById(clues);
    }

    @Override
    public Clues selectCluesById(int cluesId) throws Exception{
        return cluesDao.selectCluesById(cluesId);
    }

    @Override
    public void updateCluesTransform(Clues clues) throws Exception {
        cluesDao.updateCluesTransform(clues);
    }

    @Override
    public List<String> selectCNumber() throws Exception {
        return cluesDao.selectCNumber();
    }

    @Override
    public List<Clues> findByCluesNameAndMobile(String name, String mobile) throws Exception {
        return cluesDao.findByCluesNameAndMobile(name,mobile);
    }
}
