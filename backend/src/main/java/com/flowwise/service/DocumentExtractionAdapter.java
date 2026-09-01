package com.flowwise.service;

import com.flowwise.entity.DocumentCapture;

public interface DocumentExtractionAdapter {
    DocumentExtractionResult extract(DocumentCapture capture);
}
