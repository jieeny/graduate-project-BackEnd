package com.lgh.WebSocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.lgh.entity.onlineDoctorInfo;
import com.lgh.entity.onlineDoctorVO;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 * @author 李广辉
 * @date 2023/2/22 22:57
 */
public class WebSocketEncoder implements Encoder.Text<onlineDoctorVO> {
    @Override
    public String encode(onlineDoctorVO doctorInfo) throws EncodeException {
        try{
            JsonMapper jsonMapper=new JsonMapper();
            return jsonMapper.writeValueAsString(doctorInfo);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
