package com.ssafy.maryfarmnotifyservice.service;

import com.ssafy.maryfarmnotifyservice.domain.notify.AlarmType;
import com.ssafy.maryfarmnotifyservice.domain.notify.Notify;
import com.ssafy.maryfarmnotifyservice.repository.NotifyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class NotifyService {
    private final NotifyRepository notifyRepository;

    @Transactional
    public Notify saveNotify(String type, String content, String userId) {
        Notify notify = Notify.of(AlarmType.valueOf(type), content, true, userId);
        Notify saveNotify = notifyRepository.save(notify);
        return saveNotify;
    }
}
