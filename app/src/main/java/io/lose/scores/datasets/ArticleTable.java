package io.lose.scores.datasets;

import io.pivotal.arca.provider.Column;
import io.pivotal.arca.provider.SQLiteTable;
import io.pivotal.arca.provider.Unique;

public class ArticleTable extends SQLiteTable {

    public interface Columns extends SQLiteTable.Columns {
        @Unique(Unique.OnConflict.REPLACE)
        @Column(Column.Type.INTEGER) String ID = "id";
    }
}
