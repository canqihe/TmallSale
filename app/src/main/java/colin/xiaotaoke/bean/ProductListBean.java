package colin.xiaotaoke.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Colin on 2017/3/8 18:04.
 * 邮箱：cartier_he@163.com
 * 微信：cartier_he
 */

public class ProductListBean implements Serializable {

    public TbkUatmFavoritesGetResponseEntity tbk_uatm_favorites_get_response;

    public TbkUatmFavoritesGetResponseEntity getTbk_uatm_favorites_get_response() {
        return tbk_uatm_favorites_get_response;
    }

    public void setTbk_uatm_favorites_get_response(TbkUatmFavoritesGetResponseEntity tbk_uatm_favorites_get_response) {
        this.tbk_uatm_favorites_get_response = tbk_uatm_favorites_get_response;
    }

    public static class TbkUatmFavoritesGetResponseEntity {
        /**
         * total_results : 10
         * request_id : qm4nvwaq1xpw
         */

        public ResultsEntity results;
        public int total_results;
        public String request_id;

        public ResultsEntity getResults() {
            return results;
        }

        public void setResults(ResultsEntity results) {
            this.results = results;
        }

        public int getTotal_results() {
            return total_results;
        }

        public void setTotal_results(int total_results) {
            this.total_results = total_results;
        }

        public String getRequest_id() {
            return request_id;
        }

        public void setRequest_id(String request_id) {
            this.request_id = request_id;
        }

        public static class ResultsEntity {
            public List<TbkFavoritesEntity> tbk_favorites;

            public List<TbkFavoritesEntity> getTbk_favorites() {
                return tbk_favorites;
            }

            public void setTbk_favorites(List<TbkFavoritesEntity> tbk_favorites) {
                this.tbk_favorites = tbk_favorites;
            }

            public static class TbkFavoritesEntity {
                /**
                 * favorites_id : 3553662
                 * favorites_title : 家电
                 * type : 2
                 */

                public int favorites_id;
                public String favorites_title;
                public int type;

                public int getFavorites_id() {
                    return favorites_id;
                }

                public void setFavorites_id(int favorites_id) {
                    this.favorites_id = favorites_id;
                }

                public String getFavorites_title() {
                    return favorites_title;
                }

                public void setFavorites_title(String favorites_title) {
                    this.favorites_title = favorites_title;
                }

                public int getType() {
                    return type;
                }

                public void setType(int type) {
                    this.type = type;
                }
            }
        }
    }
}
