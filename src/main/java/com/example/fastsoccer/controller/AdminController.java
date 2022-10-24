package com.example.fastsoccer.controller;

import com.example.fastsoccer.entity.OwnPitch;
import com.example.fastsoccer.entity.UserEntity;
import com.example.fastsoccer.repository.BookingService;
import com.example.fastsoccer.repository.OwnPitchRepository;
import com.example.fastsoccer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static java.sql.Types.NULL;

//chức năng cho quản trị viên
@Controller
/*@RequestMapping("/admin")*/
public class AdminController {
    @Autowired
    OwnPitchRepository ownPitchRepository;
    @Autowired
    UserRepository userRepository;
@Autowired
    BookingService bookingService;
    @GetMapping("/admin")
    public String test(Model model){
        //tổng số người dùng
        int countUser= userRepository.countUser();
        model.addAttribute("counUser",countUser);
        //tổng số sân
        int countOwnPitch= ownPitchRepository.countOwnPitch();
        model.addAttribute("counOwnPitch",countOwnPitch);
        //tổng số lịch đặt sân
        int countBooking= bookingService.countBooking();
        model.addAttribute("countBooking",countBooking);
        //tổng doanh thu
        if(countBooking>0){
            int sumAmountBooking= bookingService.sumAmountBooking();
            model.addAttribute("sumAmountBooking",sumAmountBooking);
        }else {
            int sumAmountBooking=0;
            model.addAttribute("sumAmountBooking",sumAmountBooking);
        }
        return "admin/dashboard";
    }

    @GetMapping("/frofileAdmin")
    public String profileAdmin(Model model){
        List<UserEntity> userEntities = userRepository.findAll();
        model.addAttribute("userList", userEntities);
        return "admin/user";
    }


//hiển thị danh sách sân chờ duyệt

    //detail sân chờ duyệt

    @GetMapping("/loadPitchNotAllow")
    public String loadPitchNotAllow(Model model) {
       /* List<UserEntity> userEntities = userRepository.findAll();
        model.addAttribute("userList", userEntities);*/
       /* List<OwnPitch> ownPitchList=ownPitchRepository.findOwnPitchWatting(); //hiển thị sân chưa xác nhận
        model.addAttribute("ownPitchList", ownPitchList);*/
      //  List<OwnPitch> ownPitchListOk=ownPitchRepository.findOwnPitchSuccess(); //hiển thị sân đã xác nhận
       // model.addAttribute("ownPitchListOk", ownPitchListOk);
        List<OwnPitch> ownPitchListOk=ownPitchRepository.findAll();
        model.addAttribute("ownPitchListOk", ownPitchListOk);
        return "admin/tables";
    }
    //xem thông tin đầy đủ của sân và xét duyệt
    @GetMapping("/update")
    public ModelAndView update(Model model, @RequestParam("id") Long id) {
        ModelAndView mav = new ModelAndView("admin/detail");
        OwnPitch ownPitch = ownPitchRepository.findById(id).get();
        mav.addObject("ownPitch", ownPitch);
        return mav;
    }
    //xét duyệt sân
    @PostMapping("/updateStatus")
    public String updateStatus(@ModelAttribute("obj") OwnPitch ownPitch) {
        ownPitch.setStatus(false);
        ownPitchRepository.save(ownPitch);
        return "redirect:/loadPitchNotAllow";
    }

    //chuyển sang trang tạo tài khoản cho chủ sân bóng
    @GetMapping("/createacount")
    public ModelAndView createAcount(Model model, @RequestParam("id") Long id) {
        ModelAndView mav = new ModelAndView("admin/createAccountOwn");
        OwnPitch ownPitch = ownPitchRepository.findById(id).get();
        mav.addObject("ownPitch", ownPitch);
        model.addAttribute("user", new UserEntity());
        return mav;
    }
    //tạo tài khoản cho chủ sân bóng

    @PostMapping("/createAccountOwn")
    public String processRegister(UserEntity user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        OwnPitch ownPitch =ownPitchRepository.findById(user.getIdOwn()).get() ;
        ownPitch.setStatus(true);
        ownPitch.setDisable(true);
        ownPitchRepository.save(ownPitch);
        user.setPassword(encodedPassword);
        user.setRole("OWN");
        userRepository.save(user);
        return "redirect:/loadPitchNotAllow";
    }
   /*
   * @author: HieuMM
   * @since: 22-Jul-22 8:56 AM
   * @description-VN:  Ẩn sân bóng
   * @description-EN:
   * @param:
   * */
    @GetMapping("/disablePitch")
    public String disablePitch(@RequestParam("id") Long id) {
        OwnPitch ownPitch=ownPitchRepository.findById(id).get();
        ownPitch.setDisable(false);
        ownPitchRepository.save(ownPitch);
        return "redirect:/loadPitchNotAllow";
    }
    /*
    * @author: HieuMM
    * @since: 22-Jul-22 8:56 AM
    * @description-VN:  Hiện sân bóng
    * @description-EN:
    * @param:
    * */
    @GetMapping("/undisablePitch")
    public String undisablePitch(@RequestParam("id") Long id) {
        OwnPitch ownPitch=ownPitchRepository.findById(id).get();
        ownPitch.setDisable(true);
        ownPitchRepository.save(ownPitch);
        return "redirect:/loadPitchNotAllow";
    }
    /*
    * @author: HieuMM
    * @since: 28-Jul-22 8:44 PM
    * @description-VN:  Xóa thông tin sân
    * @description-EN:  
    * @param: 
    * */
    @GetMapping("/deletePitch")
    public String deletePitch(@RequestParam("id") Long id) {
       ownPitchRepository.deleteById(id);
        return "redirect:/loadPitchNotAllow";
    }


}
