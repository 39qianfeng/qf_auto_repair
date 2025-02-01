package com.qianfeng.service.impl;

import com.qianfeng.dto.AppointmentDTO;
import com.qianfeng.entity.Appointment;
import com.qianfeng.mapper.AppointmentMapper;
import com.qianfeng.service.AppointmentService;
import com.qianfeng.vo.AppointmentVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentMapper appointmentMapper;

    @Override
    public List<AppointmentVO> getAllAppointments() {
        List<Appointment> appointments = appointmentMapper.selectList(null);
        return appointments.stream().map(this::convertToVO).collect(Collectors.toList());
    }

    @Override
    public AppointmentVO getAppointmentById(Integer id) {
        Appointment appointment = appointmentMapper.selectById(id);
        return convertToVO(appointment);
    }

    @Override
    public AppointmentVO createAppointment(AppointmentDTO appointmentDTO) {
        Appointment appointment = new Appointment();
        BeanUtils.copyProperties(appointmentDTO, appointment);
        appointmentMapper.insert(appointment);
        return convertToVO(appointment);
    }

    @Override
    public AppointmentVO updateAppointment(Integer id, AppointmentDTO appointmentDTO) {
        Appointment appointment = appointmentMapper.selectById(id);
        BeanUtils.copyProperties(appointmentDTO, appointment);
        appointmentMapper.updateById(appointment);
        return convertToVO(appointment);
    }

    @Override
    public void deleteAppointment(Integer id) {
        appointmentMapper.deleteById(id);
    }

    private AppointmentVO convertToVO(Appointment appointment) {
        AppointmentVO vo = new AppointmentVO();
        BeanUtils.copyProperties(appointment, vo);
        // 可以在这里添加额外的字段转换逻辑
        return vo;
    }
}
   