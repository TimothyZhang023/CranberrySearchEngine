package com.zts1993.wc.ansj;

import com.zts1993.wc.util.ISegmentation;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.BaseAnalysis;

import java.util.List;

/**
 * Created by TianShuo on 2015/3/22.
 */
public class BaseSegmentation implements ISegmentation {

    @Override
    public List<Term> parse(String input) {
        return BaseAnalysis.parse(input);
    }

}
