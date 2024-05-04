package com.menu.app.infrastructure.receivers.web.commons;

public record ResponseApi<T>(T data, String message) { }
