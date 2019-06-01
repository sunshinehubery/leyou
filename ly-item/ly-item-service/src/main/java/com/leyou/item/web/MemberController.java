package com.leyou.item.web;

import com.leyou.common.pojo.BrandQueryByPageParameter;
import com.leyou.common.vo.PageResult;
import com.leyou.member.bo.MemberBo;
import com.leyou.member.bo.RankBo;
import com.leyou.member.pojo.Member;
import com.leyou.item.service.MemberService;
import com.leyou.member.vo.MemberVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

/**
 * @description:
 * @author: furong
 * @date: 2019/5/22 13:51
 * @Version: 1.0
 **/
@RestController
@RequestMapping("member")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @GetMapping("/page")
    public ResponseEntity<PageResult<Member>> queryMemberByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "desc", defaultValue = "false") Boolean desc,
            @RequestParam(value = "key", required = false) String key) throws ParseException {
        BrandQueryByPageParameter brandQueryByPageParameter = new BrandQueryByPageParameter(page,rows,sortBy,desc,key);
        return ResponseEntity.ok(memberService.queryMemberByPage(brandQueryByPageParameter));
    }

    @PostMapping
    public ResponseEntity<Void> saveMember(Member member){
        memberService.saveMember(member);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/id")
    public ResponseEntity<Member> getMemberById(@Param("id") Long id){
        return ResponseEntity.ok(memberService.getMemberById(id));
    }

    @PutMapping
    public ResponseEntity<Void> editMember(Member member) {
        memberService.editMember(member);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @DeleteMapping("id/{id}")
    public ResponseEntity<Void> deleteMemeber(@PathVariable("id") String id){
        String separator = "-";
        if(id.contains(separator)){
            String[] ids = id.split(separator);
            for(String i:ids){
                memberService.deleteMember(Long.parseLong(i));
            }
        }else{
            memberService.deleteMember(Long.parseLong(id));
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @GetMapping("rankBo")
    public  ResponseEntity<List<RankBo>> queryRankBo(){
        return ResponseEntity.ok(memberService.queryRankBo());
    }

    @GetMapping("desc")
    public ResponseEntity<List<MemberBo>> queryMembers(){

        return ResponseEntity.ok(memberService.queryMember());
    }

    @PostMapping("login")
    public ResponseEntity<Member> login(Member member){
        return ResponseEntity.ok(memberService.login(member));
    }
}
