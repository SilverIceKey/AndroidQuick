package com.sdwfqin.quicklib.utils.tangram;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TangramListModel {
    public String type = TangramListType.ContainerOneColumn;
    public HashMap<String,Object> style = new HashMap();
    public List<TangramItemModel> items = new ArrayList<>();
}
