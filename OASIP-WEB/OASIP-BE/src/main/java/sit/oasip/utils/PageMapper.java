package sit.oasip.utils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;


import java.util.List;

public class PageMapper {
    public <T> Page<T> mapToPage(Pageable pageable, List<T> listEventDTO) {
        int start = (int) pageable.getOffset();
        int end = (int) ((start + pageable.getPageSize()) > listEventDTO.size() ? listEventDTO.size()
                : (start + pageable.getPageSize()));
        Page<T> pageEvent = new PageImpl<T>(listEventDTO.subList(start, end), pageable,
                listEventDTO.size());
        return pageEvent;
    }
    
}
