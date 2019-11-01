package com.tensquare.qa.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
/**
 * 实体类
 * @author Administrator
 *
 */
@Entity
@Table(name="tb_reply")
public class Reply implements Serializable{

	@Id
	private String id;//编号


	
	private String problemid;//问题ID
	private String content;//回答内容
	private String createtime;//创建日期
	private String updatetime;//更新日期
	private String userid;//回答人ID
	private String nickname;//回答人昵称

	
	public String getId() {		
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getProblemid() {		
		return problemid;
	}
	public void setProblemid(String problemid) {
		this.problemid = problemid;
	}

	public String getContent() {		
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	public String getCreatetime() {		
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getUpdatetime() {		
		return updatetime;
	}
	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	public String getUserid() {		
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getNickname() {		
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}


	
}
