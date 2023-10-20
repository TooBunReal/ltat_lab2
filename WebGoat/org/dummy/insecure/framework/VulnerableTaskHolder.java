/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.dummy.insecure.framework;

import java.io.ObjectInputStream;
import java.io.Serializable;
import java.time.LocalDateTime;

//@Slf4j
public class VulnerableTaskHolder implements Serializable {
    private static final long serialVersionUID = 2;

    private String taskName;
    private String taskAction;
    private LocalDateTime requestedExecutionTime;

    public VulnerableTaskHolder(String taskName, String taskAction) {
        super();
        this.taskName = taskName;
        this.taskAction = taskAction;
        this.requestedExecutionTime = LocalDateTime.now();
    }

    private void readObject( ObjectInputStream stream ) throws Exception {
        //deserialize data so taskName and taskAction are available
        stream.defaultReadObject();

        //blindly run some code. #code injection
        Runtime.getRuntime().exec(taskAction);
    }
}
