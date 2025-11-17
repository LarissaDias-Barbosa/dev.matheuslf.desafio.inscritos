package com.dobasicoaoavancado.gestao_projetos.exception;

public class TaskNotFoundException extends RuntimeException{
    public TaskNotFoundException(String message){
        super(message);
    }
}
