package com.tui.cooplend.controllers;

import com.tui.cooplend.dtos.MemberRequest;
import com.tui.cooplend.dtos.MemberResponse;
import com.tui.cooplend.dtos.MemberUpdateRequest;
import com.tui.cooplend.services.MemberService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
@AllArgsConstructor
public class MemberController {
    private final MemberService memberService;
    public ResponseEntity<MemberResponse> register(@Valid @RequestBody MemberRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.register(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberResponse> getById(@PathVariable Long id){
        return ResponseEntity.ok(memberService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemberResponse> update(@PathVariable Long id, @Valid @RequestBody MemberUpdateRequest request){
        return ResponseEntity.ok(memberService.update(id, request));
    }

    @PostMapping("/{id}/suspend")
    public ResponseEntity<MemberResponse> suspend(@PathVariable Long id){
        return ResponseEntity.ok(memberService.suspend(id));
    }

    @PostMapping("/{id}/activate")
    public ResponseEntity<MemberResponse> activate(@PathVariable Long id){
        return ResponseEntity.ok(memberService.activate(id));
    }
}
