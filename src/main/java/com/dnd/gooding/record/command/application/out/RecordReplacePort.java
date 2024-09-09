package com.dnd.gooding.record.command.application.out;

import com.dnd.gooding.record.command.dto.RecordPlace;
import java.util.List;

public interface RecordReplacePort {

    List<RecordPlace> getPlaces(String keyword, int page, int size);
}
