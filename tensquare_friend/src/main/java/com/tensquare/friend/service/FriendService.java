package com.tensquare.friend.service;

import com.tensquare.friend.dao.FriendDao;
import com.tensquare.friend.dao.NoFriendDao;
import com.tensquare.friend.pojo.Friend;
import com.tensquare.friend.pojo.NoFriend;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class FriendService {

    @Resource
    private FriendDao friendDao;

    @Resource
    private NoFriendDao noFriendDao;
    public int addFriend(String userid, String friendid) {
        System.out.println("userid:"+userid+"-----"+friendid);
      Friend friend= friendDao.findByUseridAndFriendid(userid,friendid);

        //先判断userid到friendid是否有数据，重复添加
        if (friend!=null){
            return  0;
        }
        //直接添加好友，类型值为  0--单向喜欢 1--双向喜欢
        friend=new Friend();
                friend.setUserid(userid);
                friend.setFriendid(friendid);
                friend.setIslike("0");
                friendDao.save(friend);
        //判断friendid到userid是否有数据，值为1

        if (friendDao.findByUseridAndFriendid(friendid,userid)!=null){
              friendDao.updateIslIke("1",userid,friendid);
              friendDao.updateIslIke("1",friendid,userid);
        }
                 return 1;
    }

    public int addNoFriend(String userid, String friendid) {
         NoFriend noFriend= noFriendDao.findByUseridAndFriendid(userid,friendid);
        if (noFriend!=null){
            return 0;
        }
        noFriend=new NoFriend();
        noFriend.setUserid(userid);
        noFriend.setFriendid(friendid);

        noFriendDao.save(noFriend);
        return 1;
    }

    public void deleteFriend(String userid, String friendid) {
        //删除userid-friendid的数据
           friendDao.deleteFriend(userid,friendid);
        //friendid-userid 的islike 为0
           friendDao.updateIslIke("0",userid,friendid);
        //非好友表添加数据
        noFriendDao.save(new NoFriend(userid,friendid));
    }
}
