package com.bayee.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bayee.dao.SysTagInfoMapper;
import com.bayee.dao.SysTagPalceRelationMapper;
import com.bayee.dao.SysTagTypeInfoMapper;
import com.bayee.model.SysTagChirdObject;
import com.bayee.model.SysTagInfo;
import com.bayee.model.SysTagPalceRelation;
import com.bayee.model.SysTagTypeInfo;

@Service
public class SysTagService {
  @Autowired
  private SysTagInfoMapper sysTagInfoMapper;
  @Autowired
  private SysTagPalceRelationMapper sysTagPalceRelationMapper;
  @Autowired
  private SysTagTypeInfoMapper sysTagTypeInfoMapper;
  
  /**
   * 获取标签类型
   * @param para_map
   * @return
   */
  public List<SysTagTypeInfo> selectSysTagTypeInfoByMap(Map<String,Object> para_map){
	  return sysTagTypeInfoMapper.selectSysTagTypeInfoByMap(para_map);
  }
  
  /**
   * 获取标签信息
   * @param para_map
   * @return
   */
  public List<SysTagInfo> selectSysTagInfoByMap(Map<String,Object> para_map){
	  return sysTagInfoMapper.selectSysTagInfoByMap(para_map);
  }
  
  /**
   * 保存标签和点位的关系
   * @param sysTagPalceRelation
   */
  public void insertSysTagPalceRelation(SysTagPalceRelation sysTagPalceRelation) {
	  sysTagPalceRelationMapper.insert(sysTagPalceRelation);
  }
  
  /**
   * 更新或删除标签和点位的关系
   * @param sysTagPalceRelation
   */
  public void deleteSysTagPalceRelationByMap(Map<String,Object> para_map) {
	  sysTagPalceRelationMapper.deleteSysTagPalceRelationByMap(para_map);
  }
  
  /**
   * 根据点位和标签查询关联记录
   * @param sysTagPalceRelation
   */
  public List<SysTagPalceRelation> seleteSysTagPalceRelationByMap(Map<String,Object> para_map) {
	  return sysTagPalceRelationMapper.seleteSysTagPalceRelationByMap(para_map);
  }
  /**
   * 新增标签
   * @param sysTagInfo
   */
  public void addSysTagInfo(SysTagInfo sysTagInfo) {
	  sysTagInfoMapper.insert(sysTagInfo);
  }
  
  /**
   * 更新或逻辑删除标签信息
   * @param sysTagInfo
   */
  public void updateSysTagInfo(SysTagInfo sysTagInfo) {
	  sysTagInfoMapper.updateByPrimaryKeySelective(sysTagInfo);
  }
  
  /**
   * 获取标签的下的子标签或点位信息
   * @param para_map
   * @return
   */
  public List<SysTagChirdObject> selectChirdDataByMap(Map<String,Object> para_map){
	  return sysTagInfoMapper.selectChirdDataByMap(para_map);
  }
  
  /**
   * 通过主键获取标签信息
   * @param tag_id
   * @return
   */
  public SysTagInfo selectSysTagInfoById(Integer tag_id) {
	  return sysTagInfoMapper.selectByPrimaryKey(tag_id);
  }
  
  /**
   * 获取标签的下的子标签或点位总数
   * @param para_map
   * @return
   */
  public int selectChirdDataCountByMap(Map<String,Object> para_map) {
	  return sysTagInfoMapper.selectChirdDataCountByMap(para_map);
  }
  
}
