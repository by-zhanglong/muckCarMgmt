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
   * ��ȡ��ǩ����
   * @param para_map
   * @return
   */
  public List<SysTagTypeInfo> selectSysTagTypeInfoByMap(Map<String,Object> para_map){
	  return sysTagTypeInfoMapper.selectSysTagTypeInfoByMap(para_map);
  }
  
  /**
   * ��ȡ��ǩ��Ϣ
   * @param para_map
   * @return
   */
  public List<SysTagInfo> selectSysTagInfoByMap(Map<String,Object> para_map){
	  return sysTagInfoMapper.selectSysTagInfoByMap(para_map);
  }
  
  /**
   * �����ǩ�͵�λ�Ĺ�ϵ
   * @param sysTagPalceRelation
   */
  public void insertSysTagPalceRelation(SysTagPalceRelation sysTagPalceRelation) {
	  sysTagPalceRelationMapper.insert(sysTagPalceRelation);
  }
  
  /**
   * ���»�ɾ����ǩ�͵�λ�Ĺ�ϵ
   * @param sysTagPalceRelation
   */
  public void deleteSysTagPalceRelationByMap(Map<String,Object> para_map) {
	  sysTagPalceRelationMapper.deleteSysTagPalceRelationByMap(para_map);
  }
  
  /**
   * ���ݵ�λ�ͱ�ǩ��ѯ������¼
   * @param sysTagPalceRelation
   */
  public List<SysTagPalceRelation> seleteSysTagPalceRelationByMap(Map<String,Object> para_map) {
	  return sysTagPalceRelationMapper.seleteSysTagPalceRelationByMap(para_map);
  }
  /**
   * ������ǩ
   * @param sysTagInfo
   */
  public void addSysTagInfo(SysTagInfo sysTagInfo) {
	  sysTagInfoMapper.insert(sysTagInfo);
  }
  
  /**
   * ���»��߼�ɾ����ǩ��Ϣ
   * @param sysTagInfo
   */
  public void updateSysTagInfo(SysTagInfo sysTagInfo) {
	  sysTagInfoMapper.updateByPrimaryKeySelective(sysTagInfo);
  }
  
  /**
   * ��ȡ��ǩ���µ��ӱ�ǩ���λ��Ϣ
   * @param para_map
   * @return
   */
  public List<SysTagChirdObject> selectChirdDataByMap(Map<String,Object> para_map){
	  return sysTagInfoMapper.selectChirdDataByMap(para_map);
  }
  
  /**
   * ͨ��������ȡ��ǩ��Ϣ
   * @param tag_id
   * @return
   */
  public SysTagInfo selectSysTagInfoById(Integer tag_id) {
	  return sysTagInfoMapper.selectByPrimaryKey(tag_id);
  }
  
  /**
   * ��ȡ��ǩ���µ��ӱ�ǩ���λ����
   * @param para_map
   * @return
   */
  public int selectChirdDataCountByMap(Map<String,Object> para_map) {
	  return sysTagInfoMapper.selectChirdDataCountByMap(para_map);
  }
  
}
