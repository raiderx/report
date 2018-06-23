package org.karpukhin.report.job;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class JobExecutor {

    @NonNull
    private final Runnable runnable;

    public void execute(Map<String, Object> data) {
        try {
            JobContext.set(data);
            runnable.run();
        } finally {
            JobContext.remove();
        }
    }
}
