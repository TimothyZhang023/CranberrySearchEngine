package com.zts1993.wc.ansj;

import com.zts1993.wc.util.ISegmentation;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.NlpAnalysis;

import java.util.List;

/**
 * Created by TianShuo on 2015/3/22.
 */
public class NlpSegmentation implements ISegmentation {


    @Override
    public List<Term> parse(String input) {
        return NlpAnalysis.parse(input);
    }


}
