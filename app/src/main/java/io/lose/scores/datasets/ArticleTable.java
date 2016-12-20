package io.lose.scores.datasets;

import io.pivotal.arca.provider.Column;
import io.pivotal.arca.provider.SQLiteTable;
import io.pivotal.arca.provider.Unique;

public class ArticleTable extends SQLiteTable {

    public interface Columns extends SQLiteTable.Columns {
        @Unique(Unique.OnConflict.REPLACE)
        @Column(Column.Type.INTEGER) String ID = "id";
        @Column(Column.Type.TEXT) String LINK = "link";
        @Column(Column.Type.TEXT) String TITLE = "title";
        @Column(Column.Type.TEXT) String BYLINE = "byline";
        @Column(Column.Type.TEXT) String PUBLISHED_AT = "published_at";
        @Column(Column.Type.TEXT) String SMALL_IMAGE = "small_image";
        @Column(Column.Type.TEXT) String MEDIUM_IMAGE = "medium_image";
        @Column(Column.Type.TEXT) String LARGE_IMAGE = "large_image";
    }
}
