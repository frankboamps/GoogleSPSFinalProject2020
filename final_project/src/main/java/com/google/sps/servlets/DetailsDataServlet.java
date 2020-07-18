// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps.servlets;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import com.google.sps.data.Cause;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/details-data")
public class DetailsDataServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    
    response.setContentType("text/html;");
    PrintWriter out = response.getWriter();

    String postId = request.getParameter("id");

    Cause selectedDetailsCause = null;

    Query query = new Query("CauseDon").addSort("timestamp", SortDirection.DESCENDING);
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);
    
    for (Entity entity : results.asIterable()) {
        long id = entity.getKey().getId();
        if((""+id).equals(postId)){
            System.out.println("post id =" + postId + " " + "id =" + "" +id);
            String title = (String) entity.getProperty("title");
            String description = (String) entity.getProperty("description");
            String imageUrl = (String) entity.getProperty("image");
            selectedDetailsCause = new Cause(id, title, description, imageUrl);
        }
    }

    out.println("<img src= " + selectedDetailsCause.getImageUrl() + ">");
    out.println("<h2>" + selectedDetailsCause.getTitle() + "</h2>");
    out.println("<p>" + selectedDetailsCause.getDescription() + "</p>");
     
  }
}