package com.jpmsoft.common.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class AiPromptGenerateDTO {
    private String type;
    private String problem;
    private List<HistoryDialogueDTO> historyDialogue;
}
