/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.blf2.Db;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.ModelMaker;

import java.io.*;

/**
 * Created by blf2 on 17-6-28.
 */
public class ModelOp {

    public static String insertData(String filePath){
        InputStreamReader in = null;
        try {
            in = new InputStreamReader(new FileInputStream(new File(filePath)),"UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
           return "未找到ＯＷＬ文件";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            System.out.println("未知字符集");
        }
        ModelMaker modelMaker = ModelFactory.createModelRDBMaker(DbDriver.idbConnection);
        Model defModel = null;
        if(DbDriver.idbConnection.containsModel("militaryDB")){
            defModel = modelMaker.openModel("militaryDB",true);
            System.out.println("打开已存在的模型");
        }else{
            defModel = modelMaker.createModel("militaryDB");
            System.out.println("创建了一个新模型");
        }
        OntModelSpec spec = new OntModelSpec(OntModelSpec.OWL_MEM);
        OntModel dbModel = ModelFactory.createOntologyModel(spec,defModel);
        dbModel.read(in,null);
        dbModel.commit();
        System.out.println("已将数据存入数据库，等待关闭资源...");
        DbDriver.closeConnection();
        try {
            in.close();
            dbModel.close();
        } catch (IOException e) {
            return "关闭文件失败";
        }
       return "导入成功";
    }

}
