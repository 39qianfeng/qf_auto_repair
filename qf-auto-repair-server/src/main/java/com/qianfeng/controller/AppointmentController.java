package com.qianfeng.controller;

import com.qianfeng.dto.AppointmentDTO;
import com.qianfeng.result.Result;
import com.qianfeng.service.AppointmentService;
import com.qianfeng.vo.AppointmentVO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@Api(tags = "预约相关接口")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping
    public Result<List<AppointmentVO>> getAllAppointments() {
        List<AppointmentVO> appointments = appointmentService.getAllAppointments();
        return Result.success(appointments);
    }

    @GetMapping("/{id}")
    public Result<AppointmentVO> getAppointmentById(@PathVariable Integer id) {
        AppointmentVO appointment = appointmentService.getAppointmentById(id);
        return Result.success(appointment);
    }

    @PostMapping
    public Result<AppointmentVO> createAppointment(@RequestBody AppointmentDTO appointmentDTO) {
        AppointmentVO appointment = appointmentService.createAppointment(appointmentDTO);
        return Result.success(appointment);
    }

    @PutMapping("/{id}")
    public Result<AppointmentVO> updateAppointment(@PathVariable Integer id, @RequestBody AppointmentDTO appointmentDTO) {
        AppointmentVO appointment = appointmentService.updateAppointment(id, appointmentDTO);
        return Result.success(appointment);
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteAppointment(@PathVariable Integer id) {
        appointmentService.deleteAppointment(id);
        return Result.success();
    }
}