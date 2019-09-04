package com.trasail.frp.service.impl;

import com.alibaba.fastjson.JSON;
import com.trasail.frp.common.utils.Objects;
import com.trasail.frp.common.utils.RediskeyConstants;
import com.trasail.frp.dao.IChannelInfoMapper;
import com.trasail.frp.entity.ChannelInfo;
import com.trasail.frp.service.IChannelInfoService;
import com.trasail.frp.service.RedisClusterClient;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisCluster;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
@Service
public class ChannelInfoServiceImpl implements IChannelInfoService {
    private org.slf4j.Logger logger = LoggerFactory.getLogger(ChannelInfoServiceImpl.class);

    @Autowired
    private IChannelInfoMapper mChannelInfoMapper;		//用AutoWired注入DB层

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RedisClusterClient clusterClient;


    /*@Autowired
    private JedisCluster jedisCluster;*/

    @Transactional(readOnly=true)	//数据库的读取方式为：只读
    @Override
    public List<ChannelInfo> getAll() {
        List<ChannelInfo> res = null;
        try {
            res = mChannelInfoMapper.getAll(M_TAB_NAME);
            if(res == null || res.size()<0) {
                logger.error("查询数据库取出所有的渠道数据，无数据");
                return new ArrayList<ChannelInfo>();
            }

        } catch (Exception e) {
            logger.error("查询数据库取出所有的渠道数据报错",e);
        }
		/*res = mChannelInfoMapper.getAll(M_TAB_NAME);*/
        if(null == res) {
            res = new ArrayList<ChannelInfo>();
        }
        return res;
    }

    @Override
    public ChannelInfo getChanInfoByChanNo(String chanNo) {
        ChannelInfo channelInfoStr = null;
        try {
            String key = RediskeyConstants.CHANNEL_INFO_KEY_BY_CHNO + chanNo;
            Object o = clusterClient.get(key);
           /* Object o = redisTemplate.opsForValue().get(key);*/
            String s = JSON.toJSONString(o);
           /* String s = jedisCluster.get(key);*/
            //缓存中获取不到，查询数据库，并保存在缓存中
            if(s==null){
                System.out.println("缓存里有数据，数据为："+channelInfoStr.toString());
            }else {
                channelInfoStr = JSON.parseObject(s, ChannelInfo.class);
            }
            if(Objects.isEmpty(channelInfoStr)) {
                System.out.println("不在缓存里，去数据库查！");
                channelInfoStr = mChannelInfoMapper.getChanInfoByChanNo(M_TAB_NAME, chanNo);
                if(Objects.isEmpty(channelInfoStr)) {
                    System.out.println("数据库也没有！");
                    logger.error("根据chanNo:{}查询渠道数据，无数据",chanNo);
                    return null;
                }
                //缓存60分钟
                clusterClient.put(key,channelInfoStr);
                /*redisTemplate.opsForValue().set(key, channelInfoStr);*/
                /*String s1 = JSON.toJSONString(channelInfoStr);
                jedisCluster.set(key,s1);*/
            }
        } catch (Exception e) {
            logger.error("根据chanNo:{}查询渠道数据报错，信息为：{}",chanNo,e);
        }
        //return mChannelInfoMapper.getChanInfoByChanNo(M_TAB_NAME, chanNo);
        return channelInfoStr;
    }

    @Override
    public String getRSAPrivKB64StrByChanNo(String chanNo) {
        if(StringUtils.isEmpty(chanNo)) {
            Logger.getAnonymousLogger().log(Level.INFO, "chanNo为空，无法查询RSA私钥！");
            return null;
        }
        String rsaPrivKStrByChanNo = null;
        try {
            String key = RediskeyConstants.RSA_PRIVATE_KEY + chanNo ;
            rsaPrivKStrByChanNo = (String) redisTemplate.opsForValue().get(key);
            //缓存中获取不到，查询数据库，并保存在缓存中
            if(StringUtils.isBlank(rsaPrivKStrByChanNo)) {
                rsaPrivKStrByChanNo = mChannelInfoMapper.getRSAPrivKStrByChanNo(M_TAB_NAME, chanNo);
                if(StringUtils.isBlank(rsaPrivKStrByChanNo)) {
                    logger.error("根据渠道号{}查询数据库私钥，无数据",chanNo);
                    return null;
                }
                redisTemplate.opsForValue().set(key, rsaPrivKStrByChanNo);
            }
        } catch (Exception e) {
            logger.error("根据渠道号{}查询数据库私钥报错",chanNo,e);
        }
        return rsaPrivKStrByChanNo;
    }

    @Override
    public boolean insertChanInfo(ChannelInfo info) {
        if(null == info) {
            Logger.getAnonymousLogger().log(Level.WARNING, "渠道信息为null，插入失败！");
            return false;
        }
        boolean res = true;
        try {
            mChannelInfoMapper.insertChanInfo(M_TAB_NAME, info);
        } catch(Exception e) {
            e.printStackTrace();
            res = false;
        }

        return res;
    }

    @Override
    public boolean updateChanInfoByChanNo(String chNo, ChannelInfo info) {
        if(StringUtils.isEmpty(chNo)) {
            Logger.getAnonymousLogger().log(Level.INFO, "渠道号为空，更新失败！");
            return false;
        }
        if(null == info) {
            Logger.getAnonymousLogger().log(Level.INFO, "info为null，更新失败！");
            return false;
        }
        mChannelInfoMapper.updateChanInfoByChanNo(getTableName(), chNo, info);
        redisTemplate.delete(RediskeyConstants.RSA_PRIVATE_KEY + chNo);
        redisTemplate.delete(RediskeyConstants.CHANNEL_INFO_KEY_BY_CHNO+chNo);
        return true;
    }

    @Override
    public ChannelInfo getChanInfoByIndex(int ind) {
        if(ind <= 0) {
            Logger.getAnonymousLogger().log(Level.INFO,
                    MessageFormat.format("ind[{0}]无效，无法获取渠道信息", ind));
            return null;
        }
        //return mChannelInfoMapper.getChanInfoByIndex(M_TAB_NAME, ind);
        ChannelInfo channelInfoStr = null;
        try {
            String key = RediskeyConstants.CHANNEL_INFO_KEY_BY_ID + ind;
            channelInfoStr = (ChannelInfo) redisTemplate.opsForValue().get(key);

            //缓存中获取不到，查询数据库，并保存在缓存中
            logger.warn("从缓存里拿出的信息为:"+channelInfoStr.toString());
            if(Objects.isEmpty(channelInfoStr)) {
                channelInfoStr = mChannelInfoMapper.getChanInfoByIndex(M_TAB_NAME, ind);
                if(Objects.isEmpty(channelInfoStr)) {
                    logger.error("根据ind:{}查询渠道数据，无数据",ind);
                    return null;
                }
                redisTemplate.opsForValue().set(key, channelInfoStr);
            }
        } catch (Exception e) {
            logger.error("根据ind:{}查询渠道数据报错",ind,e);
        }
        return channelInfoStr;
    }

    @Override
    public boolean deleteByIndex(int ind) {
        if(ind <= 0) {
            Logger.getAnonymousLogger().log(Level.INFO, MessageFormat.format("序列号【{0}】无效，无法删除！", ind));
            return false;
        }
        mChannelInfoMapper.deleteByIndex(M_TAB_NAME, ind);
        return true;
    }

    @Override
    public boolean deleteByChanNo(String chanNo) {
        if(StringUtils.isEmpty(chanNo)) {
            Logger.getAnonymousLogger().log(Level.INFO, "chan-number为空，无法删除！");
            return false;
        }
        mChannelInfoMapper.deleteByChNo(M_TAB_NAME, chanNo);
        redisTemplate.delete(RediskeyConstants.RSA_PRIVATE_KEY + chanNo);
        redisTemplate.delete(RediskeyConstants.CHANNEL_INFO_KEY_BY_CHNO+chanNo);
        return true;
    }

    @Override
    public boolean isTaskSwitchOn(String chanNo) {
        if(StringUtils.isEmpty(chanNo)) {
            Logger.getAnonymousLogger().log(Level.INFO, "渠道号为空，无法查询任务开关状态");
            return false;
        }

        Integer res = mChannelInfoMapper.getChanTaskSwitchState(getTableName(), chanNo);
        if(null == res) {
            Logger.getAnonymousLogger().log(Level.INFO, MessageFormat.format("渠道【{0}】不存在，无法查询任务开关状态", chanNo));
            return false;
        }

        return res == ChannelInfo.M_TASK_SWITCH_ON;
    }

    private static final String M_TAB_NAME = "channel_info";

    @Override
    public String getTableName() {
        return M_TAB_NAME;
    }

    private static final String M_SQL_TAB_CREATE = "_id bigint(20) NOT NULL AUTO_INCREMENT, " +
            "chan_no varchar(255) NOT NULL, " +
            "chan_desc varchar(255) NOT NULL, " +
            "task_switch int(5), " +
            "s_user_max_tasks int(5), " +
            "postback varchar(255), " +
            "rsa_pub_key_b64 varchar(1024) NOT NULL, " +
            "rsa_priv_key_b64 varchar(2048) NOT NULL, " +
            "create_time timestamp NOT NULL, " +
            "PRIMARY KEY (_id)";

    @Override
    public String getCreateTableSql() {
        return M_SQL_TAB_CREATE;
    }

}

