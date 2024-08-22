package com.spring316.common.ctrl;

import com.spring316.user.dto.Status;
import com.spring316.user.dto.UserDto;
import com.spring316.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class HomeController {


    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

//    @Autowired
//    private BoardService boardService;

    @GetMapping("/")
    public String Home(Model model){
        return "index";
    }

    @GetMapping("/term")
    public String Term(Model model){
        return "/user/joinTerm";
    }

    @GetMapping("/login")
    public String Login(Model model){
        return "/user/login";
    }

    @GetMapping("/active")
    public String active(Model model, HttpServletRequest request, HttpServletResponse response){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null){
            new SecurityContextLogoutHandler().logout(request,response,authentication);
        }
        return "/user/active";
    }

    @GetMapping("/join")
    public String JoinForm(Model model){
        return "/user/join";
    }

    @PostMapping("/joinPro")
    public String Join(Model model, UserDto userDTO){
        userService.userInsert(userDTO);
        model.addAttribute("msg", "가족이 되신걸 환영합니다.");
        model.addAttribute("url", "/spring316/");
        return "/alert";
    }

    @PostMapping("idCheckPro")
    public ResponseEntity idCheck(@RequestBody UserDto userDTO) throws Exception {
        String id = userDTO.getId();
        boolean result = userService.idCheck(id);
        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    @GetMapping("/myPage")
    public String Exindex(Model model, Principal principal){
        UserDto userDTO = userService.getId(principal.getName());
        model.addAttribute("user", userDTO);
        return "/user/myPage";
    }

    @GetMapping("/remove")
    public String remove(String id, Model model){
        UserDto userDTO = userService.getId(id);
        userDTO.setStatus(Status.OUT);
        userService.userUpdate(userDTO);
        model.addAttribute("msg", "지금까지 감사합니다.");
        model.addAttribute("url", "/logout");
        return "/alert";
    }

    @GetMapping("/myPageEdit")
    public String myPageForm(Model model, Principal principal){
        UserDto user = userService.getId(principal.getName());
        model.addAttribute("user", user);
        return "/user/myPageEdit";
    }

    @PostMapping("/myPageEdit")
    public String myPageEdit(UserDto userDTO){
        log.warn("id" + userDTO.getId());
        userService.userUpdate(userDTO);
        return "redirect:/myPage";
    }

    @GetMapping("/changePw")
    public String changePwForm(Model model, String id){
        UserDto user = userService.getId(id);
        model.addAttribute("user", user);
        return "/user/changePw";
    }

    @PostMapping("/changePw")
    public String changePw(Model model, String pw, String id){
        UserDto userDTO = userService.getId(id);
        userDTO.setPw(pw);
        userService.userchangePw(userDTO);
        model.addAttribute("url", 2);
        return "/alert";
    }
    @GetMapping("/changeEmail")
    public String changeEmailForm(Model model, String id){
        UserDto user = userService.getId(id);
        model.addAttribute("user", user);
        return "/user/changeEmail";
    }

    @PostMapping("/changeEmail")
    public String changeEmail(Model model, String email, String id){
        UserDto userDTO = new UserDto();
        userDTO.setId(id);
        userDTO.setEmail(email);
        userService.emailUpdate(userDTO);
        model.addAttribute("url", 1);
        return "/alert";
    }



    // Faq

    /*@GetMapping("/faq")
    public String Faq(Model model) {
        List<BoardDTO> faqList = boardService.faqList();
        model.addAttribute("faqList", faqList);
        return "/board/faq";
    }

    @GetMapping("/faqadd")
    public String FaqForm(Model model) {
        model.addAttribute("boarddto", new BoardDTO());
        return "/board/faqadd";
    }

    @PostMapping("/faqadd")
    public String FaqInsert(BoardDTO boardDTO){
        boardService.faqInsert(boardDTO);
        return "redirect:/faq";
    }*/




    // Notice

    /*@GetMapping("/notice")
    public String notice(Model model) {
        List<BoardDTO> noticeList = boardService.usernoticeList();
        model.addAttribute("noticeList", noticeList);
        return "/board/notice";
    }

    @GetMapping("/noticeGet")
    public String noticeGet(Model model, Integer no) {
        BoardDTO notice = boardService.NoticeGet(no);
        model.addAttribute("notice", notice);
        return "/board/noticeGet";
    }


    @GetMapping("/noticeadd")
    public String noticeForm(Model model, Principal principal) {
        model.addAttribute("boardDTO", new BoardDTO());
        model.addAttribute("prin", principal.getName());
        return "/board/noticeadd";
    }

    @PostMapping("/noticeadd")
    public String noticeInsert(BoardDTO boardDTO){
        boardService.NoticeInsert(boardDTO);
        return "redirect:/notice";
    }

    @GetMapping("/noticeEdit")
    public String noticeEditForm(Model model, Integer no) {
        BoardDTO boardDTO = boardService.NoticeGet(no);
        model.addAttribute("notice", boardDTO);
        return "/board/noticeEdit";
    }

    @PostMapping("noticeEdit")
    public String noticeEdit(BoardDTO boardDTO){
        Integer no = boardDTO.getNo();
        boardService.NoticeUpdate(boardDTO);
        return "redirect:/noticeGet?no="+no;
    }

    @GetMapping("/noticeDelete")
    public String noticeDelete(Model model, Integer no) {
        boardService.NoticeDelete(no);
        return "redirect:/notice";
    }*/

    //  Oto

    /*@GetMapping("/oto")
    public String otoForm(Model model){
        return "/board/oto";
    }

    @PostMapping("/oto")
    public String oto(Model model, BoardDTO boardDTO){
        boardService.otoInsert(boardDTO);
        model.addAttribute("url", "/team16/oto");
        model.addAttribute("msg", "해당 내용을 관리자에게 전송하였습니다. 답변까지 3~5일 소요될 수 있습니다^^ ");
        return "/alert";
    }*/
}