package com.arcplus.fm.mapper;

import java.util.List;
import com.arcplus.fm.entity.TpAsset;
import org.apache.ibatis.annotations.Mapper;


/**
 * 共同-资产分类
 * 
 * @author jv_team
 * @email 
 * @date 2020-04-17 10:31:12
 */
@Mapper
public interface TpAssetMapper  {
    int save(TpAsset entity);

    int update(TpAsset entity);

    int delete(int id);

    List<TpAsset> selectAll();
}
