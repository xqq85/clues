package jump.service.impl;

import jump.domain.Clues;
import jump.domain.PublicSea;
import jump.service.ICluesService;
import jump.service.IFileUploadService;
import jump.service.IPublicSeaCluesService;
import jump.utils.PoiUtil;
import jump.utils.ValidateFormUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

@Service
public class FileUploadServiceImpl implements IFileUploadService {

    private XSSFSheet xssfSheet = null;

    @Autowired
    private ICluesService cluesService;

    @Autowired
    private IPublicSeaCluesService publicSeaCluesService;

    @Override
    public int importExcelClues(File file) throws Exception{
        //读取File对象并转换成XSSFSheet类型对象进行处理
        try {
            //表格对象
            xssfSheet = PoiUtil.getXSSFSheet(file);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        ArrayList<Clues> clues = new ArrayList<>();
        //第一行是表名称，第二行才是数据，所以从第二行开始读取
        for (int i = 1; i <= xssfSheet.getLastRowNum(); i++) {
            //获取Excel表格指定行的对象
            XSSFRow row = xssfSheet.getRow(i);
            if (row != null) {
                Clues clue = new Clues();
                //获取线索名
                XSSFCell cluesName = row.getCell(0);
                //获取线索手机号
                XSSFCell mobile = row.getCell(1);
                //获取线索邮箱
                XSSFCell email = row.getCell(2);
                //获取线索地址
                XSSFCell address= row.getCell(3);
                //获取线索地址
                XSSFCell cluesDesc= row.getCell(4);

                //设置线索名
                if (!StringUtils.isEmpty(cluesName)) {
                    clue.setCluesName(PoiUtil.getValue(cluesName));

                }
                if (!StringUtils.isEmpty(mobile)) {
                    try {
                        ValidateFormUtil.isPhone(PoiUtil.getValue(mobile),"手机号");
                        clue.setMobile(PoiUtil.getValue(mobile));
                    }catch (RuntimeException e){
                        e.printStackTrace();
                        continue;
                    }
                }
                if (!StringUtils.isEmpty(email)) {
                    clue.setEmail(PoiUtil.getValue(email));
                }
                if (!StringUtils.isEmpty(address)) {
                    clue.setAddress(PoiUtil.getValue(address));
                }
                if (!StringUtils.isEmpty(cluesDesc)) {
                    clue.setCluesDesc(PoiUtil.getValue(cluesDesc));
                }
                clue.setEntryTime(new Date());
                clue.setTransform(0);
                clue.setTrackNumber("000");
                System.out.println(clue);
                //线索验证 已存在或者为空则不进行insert操作
                if (!StringUtils.isEmpty(clue.getCluesName()) && !StringUtils.isEmpty(clue.getMobile()) && cluesService.findByCluesNameAndMobile(clue.getCluesName(),clue.getMobile()).isEmpty() && publicSeaCluesService.findPublicSeaByNameAndMobile(clue.getCluesName(),clue.getMobile()).isEmpty()) {
                    clues.add(clue);
                }
            }
        }

        //判空
        if (!CollectionUtils.isEmpty(clues)) {
            for (Clues clue:clues) {
                cluesService.addClueToClues(clue);
                return clues.size();
            }
        }
        return 0;
    }

    @Override
    public int importExcelPublicSea(File file) throws Exception {
        //读取File对象并转换成XSSFSheet类型对象进行处理
        try {
            //表格对象
            xssfSheet = PoiUtil.getXSSFSheet(file);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        ArrayList<PublicSea> publicSeas = new ArrayList<>();
        //第一行是表名称，第二行才是数据，所以从第二行开始读取
        for (int i = 1; i <= xssfSheet.getLastRowNum(); i++) {
            //获取Excel表格指定行的对象
            XSSFRow row = xssfSheet.getRow(i);
            if (row != null) {
                PublicSea publicSea = new PublicSea();
                //获取线索名
                XSSFCell clientName = row.getCell(0);
                //获取线索手机号
                XSSFCell mobile = row.getCell(1);
                //获取线索邮箱
                XSSFCell email = row.getCell(2);
                //获取线索地址
                XSSFCell address= row.getCell(3);
                //获取线索地址
                XSSFCell clientDesc= row.getCell(4);

                //设置线索名
                if (!StringUtils.isEmpty(clientName)) {
                    publicSea.setClientName(PoiUtil.getValue(clientName));

                }
                if (!StringUtils.isEmpty(mobile)) {
                    try {
                        ValidateFormUtil.isPhone(PoiUtil.getValue(mobile),"手机号");
                        publicSea.setMobile(PoiUtil.getValue(mobile));
                    }catch (RuntimeException e){
                        e.printStackTrace();
                        continue;
                    }
                }
                if (!StringUtils.isEmpty(email)) {
                    publicSea.setEmail(PoiUtil.getValue(email));
                }
                if (!StringUtils.isEmpty(address)) {
                    publicSea.setAddress(PoiUtil.getValue(address));
                }
                if (!StringUtils.isEmpty(clientDesc)) {
                    publicSea.setClientDesc(PoiUtil.getValue(clientDesc));
                }
                publicSea.setTransform(0);
                publicSea.setTrackNumber("000");
                System.out.println(publicSea);
                //线索验证 已存在或者为空则不进行insert操作
                if (!StringUtils.isEmpty(publicSea.getClientName()) && !StringUtils.isEmpty(publicSea.getMobile()) && publicSeaCluesService.findPublicSeaByNameAndMobile(publicSea.getClientName(),publicSea.getMobile()).isEmpty() && cluesService.findByCluesNameAndMobile(publicSea.getClientName(),publicSea.getMobile()).isEmpty()) {
                    publicSeas.add(publicSea);
                }
            }
        }

        //判空
        if (!CollectionUtils.isEmpty(publicSeas)) {
            for (PublicSea publicSea:publicSeas) {
                publicSeaCluesService.addPublicSeaToPublicSea(publicSea);
                return publicSeas.size();
            }
        }
        return 0;
    }

}
