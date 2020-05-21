package com.arcplus.fm.mapper;

import java.util.List;
import java.util.Map;

import com.arcplus.fm.entity.vo.CoVehicleVo;
import com.arcplus.fm.entity.CoVehicle;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


/**
 * 
 * 
 * @author jv_team
 * @email 
 * @date 2020-05-08 10:15:32
 */
@Mapper
public interface CoVehicleMapper  {
    int save(CoVehicle entity);

    int update(CoVehicle entity);

    int delete(int id,int delete_uid);

    List<CoVehicle> selectAll();


    @Select("<script>" +
            "   select * from co_vehicle " +
            "   where vehicle_id = #{id} " +
            "</script>")
    CoVehicle getById(int id);

    @Select("<script>" +
            " SELECT" +
            "     tba.*, " +
            "     tbb.org_name branch_company_name " +
            " FROM co_vehicle tba " +
            "   LEFT JOIN co_organization tbb ON tba.branch_company_id=tbb.org_id " +

            " WHERE tba.data_status = 'N' " +

            " <if test='fromDate != null'>" +
            "     and " +
            "       tba.create_at &gt;= #{fromDate} " +
            " </if>" +

            " <if test='toDate != null'>" +
            "     and " +
            "       tba.create_at &lt;= #{toDate} " +
            " </if>" +

            " <if test='inSearch != null'>" +
            "     and " +
            "     <foreach collection='inSearch' item='value' index='key'" +
            "              open=' ' separator=' and ' close=' '>" +
            "            ${key} in " +
            "        <foreach collection='value' item='v' index='i'" +
            "              open=' (' separator=',' close=') '>" +
            "            #{v}" +
            "        </foreach>" +
            "     </foreach>" +
            " </if>" +
            " <if test='fuzzySearch != null'>" +
            "     and " +
            "     <foreach collection='fuzzySearch' item='value' index='key'" +
            "              open=' ' separator=' and ' close=' '>" +
            "         ${key} like #{value} " +
            "     </foreach>" +
            " </if>" +
            " <if test='orderBy != null'>" +
            "     order by ${orderBy}" +
            " </if>" +
            "</script>")
    List<CoVehicleVo> search(Map<String, Object> prama);



    @Select("<script>" +
            " SELECT" +
            "     tba.*, " +
            "     tbb.org_name branch_company_name " +
            " FROM co_vehicle tba " +
            "   LEFT JOIN co_organization tbb ON tba.branch_company_id=tbb.org_id " +
            " WHERE tba.data_status = 'N' and building_id=#{buildingId} and floor_id=#{floorId}" +
            " order by vehicle_id desc"+
            "</script>")
    ///通过楼层获取车位信息
    List<CoVehicleVo> selectCoVehicleVo(int buildingId, int floorId);

    @Select("<script>" +
            " SELECT count(*) counts" +
            " FROM co_vehicle tba " +
            " WHERE tba.data_status = 'N' and building_id=#{buildingId} and floor_id=#{floorId}" +
            "</script>")
        ///通过楼层获取车位信息
    int selectCoVehicleVoCount(int buildingId, int floorId);



    List<CoVehicle> checkIsExist(String carNum);

    @Select("<script>" +
            " SELECT count(*) as counts" +
            " FROM co_vehicle tba " +
            " WHERE tba.data_status = 'N' and building_id=#{buildingId} and floor_id=#{floorId} and space=#{space}" +
            " and vehicle_id != #{vehicleId}"+
            "</script>")
    int checkIsUsed(int vehicleId,int buildingId,int floorId,String space );
}
