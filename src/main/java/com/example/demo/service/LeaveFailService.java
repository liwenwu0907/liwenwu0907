package com.example.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

/**
 * @author liwenwu
 */
@Slf4j
@Component
public class LeaveFailService implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) {
        log.info("审批不通过{}", delegateExecution.getCurrentActivityId());
    }
}

