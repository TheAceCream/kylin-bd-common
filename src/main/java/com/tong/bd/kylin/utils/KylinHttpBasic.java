package com.qf58.bd.kylin.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qf58.bd.kylin.entities.cubes.*;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: weicaijia
 * Date: 2018/9/17 18:26
 * Time: 14:15
 */
public class KylinHttpBasic {

    private static final String GET = "GET";
    private static final String POST = "POST";
    private static final String PUT = "PUT";
    private static String encoding="QURNSU46S1lMSU4=";
    private static final String baseURL = "http://你服务器的ip地址:7070/kylin/api";


    /**
     * 登录
     * @param user
     * @param password
     * @return
     */
    public static String login(String user,String password){
        String method = POST;
        String para = "/user/authentication";
        byte[] key = (user+":"+password).getBytes();
        encoding = new sun.misc.BASE64Encoder().encode(key);
        return execute(para,method,null);
    }

    /**
     * 列出可查询的table
     * @param projectName
     * @return
     */
    public static String listQueryableTables(String projectName){
        String method = GET;
        String para = "/tables_and_columns?project="+projectName;
        return  execute(para,method,null);
    }


    /**
     * 列出cubes
     * @param offset 偏移量
     * @param limit 页容量
     * @param cubeName cube名称关键字
     * @param projectName 项目名字
     * @return
     */
    public static List<CubeEntity> listCubes(int offset, int limit, String cubeName, String projectName){
        String method = GET;
        if (offset<0){
            offset=0;
        }
        if (limit<0){
            limit=15;
        }
        if (cubeName==null){
            cubeName="";
        }
        if (projectName==null){
            projectName="";
        }
        String para = "/cubes?offset="+offset +"&limit="+limit +"&cubeName="+cubeName +"&projectName="+projectName;
        String result = execute(para,method,null);
        List<CubeEntity> cubeEntityList = new ArrayList<>();
        if (result!=null){
            //转换成json
            JSONArray jsonArray = JSONArray.parseArray(result);
            int size = jsonArray.size();
            for (int i = 0; i < size; i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                //拼CubeEntity
                CubeEntity cubeEntity = new CubeEntity();
                cubeEntity.setUuid(jsonObject.getString("uuid"));
                cubeEntity.setName(jsonObject.getString("name"));
                cubeEntity.setDescriptor(jsonObject.getString("descriptor"));
                cubeEntity.setModel(jsonObject.getString("model"));
                cubeEntity.setOwner(jsonObject.getString("owner"));
                cubeEntity.setProject(jsonObject.getString("project"));
                cubeEntity.setStatus(jsonObject.getString("status"));
                cubeEntity.setLastBuildTime(jsonObject.getLong("last_build_time"));
                if (jsonObject.getLong("last_build_time")!=null){
                    DateTimeFormatter ftf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    String lastBuildTime = ftf.format(LocalDateTime.ofInstant(Instant.ofEpochMilli(jsonObject.getLong("last_build_time")),ZoneId.systemDefault()));
                    cubeEntity.setLastBuildTimeStr(lastBuildTime);

                }

                cubeEntityList.add(cubeEntity);
            }
        }
        return cubeEntityList;
    }

    /**
     * 重载 列出cubes
     * @param cubeName cube的关键字
     * @param projectName 项目名
     * @return
     */
    public static List<CubeEntity> listCubes(String cubeName,String projectName){
        String method = GET;
        if (cubeName==null){
            cubeName="";
        }
        if (projectName==null){
            projectName="";
        }
        return listCubes(0,15,cubeName,projectName);
    }

    /**
     * 列出 项目下cubes的名字
     * @param cubeName
     * @param projectName
     * @return
     */
    public static List<String> listCubesName(String cubeName,String projectName){
        List<CubeEntity> cubeEntities = listCubes(cubeName,projectName);
        List<String> list = new ArrayList<>();
        if (cubeEntities!=null){
            for (CubeEntity cubeEntity : cubeEntities) {
                list.add(cubeEntity.getName());
            }
        }
        return list;
    }

    /**
     * 获取Cube详情
     * @param cubeName
     * @return
     */
    public static CubeDescEntity getCubeDes(String cubeName){
        String method = GET;
        String para = "/cube_desc/"+cubeName;
        String result = execute(para,method,null);
        CubeDescEntity cubeDescEntity = new CubeDescEntity();
        if (result!=null){
            JSONArray jsonArray = JSONArray.parseArray(result);
            for (int l=0;l<jsonArray.size();l++){
                JSONObject jsonObject = jsonArray.getJSONObject(l);
                cubeDescEntity.setUuid(jsonObject.getString("uuid"));
                cubeDescEntity.setName(jsonObject.getString("name"));
                cubeDescEntity.setModelName(jsonObject.getString("model_name"));
                cubeDescEntity.setDescription(jsonObject.getString("description"));

                cubeDescEntity.setLastModified(jsonObject.getLong("last_modified"));
                if (jsonObject.getLong("last_modified")!=null){
                    DateTimeFormatter ftf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    String lastModifiedTime = ftf.format(LocalDateTime.ofInstant(Instant.ofEpochMilli(jsonObject.getLong("last_modified")),ZoneId.systemDefault()));
                    cubeDescEntity.setLastModifiedStr(lastModifiedTime);
                }

                String dimensionsStr = jsonObject.getString("dimensions");
                String measuresStr = jsonObject.getString("measures");
                JSONArray dimensionsArray = JSONArray.parseArray(dimensionsStr);
                List<DimensionsEntity> dimensions = new ArrayList<>();
                List<MeasuresEntity> measures = new ArrayList<>();
                for (int i = 0; i < dimensionsArray.size(); i++) {
                    JSONObject dimensionsObject = dimensionsArray.getJSONObject(i);
                    DimensionsEntity dimensionsEntity = new DimensionsEntity();
                    dimensionsEntity.setName(dimensionsObject.getString("name"));
                    dimensionsEntity.setTable(dimensionsObject.getString("table"));
                    dimensionsEntity.setColumn(dimensionsObject.getString("column"));
                    //TODO: 衍生暂时没加进去，主要是因为目前没有关于衍生的使用
                    dimensions.add(dimensionsEntity);
                }
                cubeDescEntity.setDimensions(dimensions);

                JSONArray measuresArray = JSONArray.parseArray(measuresStr);
                for (int i = 0; i < measuresArray.size(); i++) {
                    JSONObject measuresObject = measuresArray.getJSONObject(i);
                    MeasuresEntity measuresEntity = new MeasuresEntity();
                    measuresEntity.setName(measuresObject.getString("name"));
                    Function function = new Function();
                    if (measuresObject.getString("function")!=null){
                        JSONObject functionObj = JSONObject.parseObject(measuresObject.getString("function"));
                        function.setExpression(functionObj.getString("expression"));
                        Map<String,String> parameter = new HashMap<>();
                        if (functionObj.getString("parameter")!=null){
                            JSONObject parameterObj = JSONObject.parseObject(functionObj.getString("parameter"));
                            parameter.put("type",parameterObj.getString("type"));
                            parameter.put("value",parameterObj.getString("value"));
                        }
                        function.setParameter(parameter);
                        measuresEntity.setFunction(function);
                    }
                    measures.add(measuresEntity);
                }
                cubeDescEntity.setMeasures(measures);
            }
        }
        return cubeDescEntity;

    }


    /**
     * 构建Cube
     * @param cubeName Cube name.
     * @param body
     * body must be json format !
     * 时间是毫秒~
     * example: {"startTime":1388563200000,"endTime":1388563200000,"buildType":"BUILD"}
     * startTime - required long Start timestamp of data to build, e.g. 1388563200000 for 2014-1-1
     * endTime - required long End timestamp of data to build
     * buildType - required string Supported build type: ‘BUILD’, ‘MERGE’, ‘REFRESH’
     * @return
     */
    public static String buildCube(String cubeName,String body){
        String method = PUT;
        String para = "/cubes/"+cubeName+"/rebuild";


        return execute(para,method,body);
    }

    /**
     * 全量构建单个cube
     * 时间默认 至 当前系统时间为准
     * @param cubeName
     * @return
     */
    public static String doseBuildCube(String cubeName){
        String body = "{\"endTime\":"+System.currentTimeMillis()+",\"buildType\":\"BUILD\"}";
        System.out.println(body);
        String method = PUT;
        String para = "/cubes/"+cubeName+"/rebuild";
        return execute(para,method,body);
    }

    /**
     * 批量构建多个cube
     * @param cubeNameList cube名字的list
     * @return
     */
    public static List<String> doseBuildCubeList(List<String> cubeNameList){
        List<String> list = new ArrayList<>();
        if (cubeNameList!=null){
            for (String cubeName : cubeNameList) {
               String result = doseBuildCube(cubeName);
               list.add(result);
            }
        }
        return list;
    }


    /**
     * 执行
     * @param para
     * @param method
     * @param body
     * @return
     */
    private static String execute(String para, String method, String body){
        StringBuilder out = new StringBuilder();
        try {
            URL url = new URL(baseURL+para);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);
            connection.setDoOutput(true);
            connection.setRequestProperty  ("Authorization", "Basic " + encoding);
            connection.setRequestProperty("Content-Type","application/json");
            if(body !=null){
                byte[] outputInBytes = body.getBytes("UTF-8");
                OutputStream os = connection.getOutputStream();
                os.write(outputInBytes);
                os.close();
            }
            InputStream content = (InputStream)connection.getInputStream();
            BufferedReader in  = new BufferedReader (new InputStreamReader (content));
            String line;
            while ((line = in.readLine()) != null) {
                out.append(line);
            }
            in.close();
            connection.disconnect();

        } catch(Exception e) {
            e.printStackTrace();
        }
        return out.toString();
    }




    public static void main(String[] args) {
        //1.登录
//        System.out.println(login("ADMIN","KYLIN"));

        //2.列出Cubes
//        System.out.println(listCubes(null,"test"));

        //3.列出Cubes的名字
//        List<String> names = listCubesName(null,"test");
//        System.out.println(names);

        //4.获取Cube详情
//        System.out.println(getCubeDes("clueCube"));
//        System.out.println(getCubeDes("project_cube"));

        //5.重构建单个Cube
//        System.out.println(doseBuildCube("project_cube"));

        //6.批量构建Cube
//        doseBuildCubeList(names);

    }
}
