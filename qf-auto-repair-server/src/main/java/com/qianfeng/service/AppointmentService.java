package com.qianfeng.service;

import com.qianfeng.dto.AppointmentDTO;
import com.qianfeng.vo.AppointmentVO;
import java.util.List;

public interface AppointmentService {
    List<AppointmentVO> getAllAppointments();
    AppointmentVO getAppointmentById(Integer id);
    AppointmentVO createAppointment(AppointmentDTO appointmentDTO);
    AppointmentVO updateAppointment(Integer id, AppointmentDTO appointmentDTO);
    void deleteAppointment(Integer id);
}