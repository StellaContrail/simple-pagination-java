/**
* Simple Pagination Library
* 
* @author	StellaContrail
* 
* Copyright (c) 2022, StellaContrail
*
* This library is released under the MIT License.
* See https://opensource.org/licenses/mit-license.php for more details.
*/

package utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Pagination {
    // hidden
    String baseURL;
    Map<String, String> params;
    String paramPage;
    
    // open
    int current;
    String next;
    int nextNum;
    int length;

    /**
       * Constructor
       * @param		baseURL The baseURL defines the root URI for all pages. 
       */
    public Pagination(String baseURL) {
        this.baseURL = baseURL;
        this.params = new HashMap<String, String>();
        this.paramPage = "";
    }

    /**
       * Constructor
       * @param		baseURL The baseURL defins the root URI for all pages. 
       * @param		paramPage Parameter used for indicating pagination index.
       */
    public Pagination(String baseURL, String paramPage) {
        this.baseURL = baseURL;
        this.params = new HashMap<String, String>();
        this.paramPage = paramPage;
    }
    
    /**
       * Constructor
       * @param		baseURL The baseURL defins the root URI for all pages. 
       * @param		params	GET parameters excluding a parameter used for indicating pagination index.
       * @param		paramPage Parameter used for indicating pagination index.
       */
    public Pagination(String baseURL, Map<String, String> params, String paramPage) {
        this.baseURL = baseURL;
        this.params = params;
        this.paramPage = paramPage;
    }
    
    private void init() {
        if (this.baseURL == null) {
            this.baseURL = "";
        }
        if (this.params == null) {
            this.params = new HashMap<String, String>();
        }
        if (this.paramPage == null) {
            this.paramPage = "";
        }

        this.next = this.baseURL + "?";
        for (String param : this.params.keySet()) {
            this.next += param + "=" + this.params.get(param) + "&";
        }
        this.next += this.paramPage + "=";
    }

    
    /**
       * getCurrent
       * @return Returns the current index number.
       */
    public int getCurrent() {
        if (this.current < 1) {
            return -1;
        }
        return this.current;
    }
    
    /**
       * getNextNum
       * @return Returns the next index number, or -1 if there's no more. 
       */
    public int getNextNum() {
        return this.nextNum;
    }

    /**
       * getNext
       * @return Returns the next page URL.
       */
    public String getNext() {
        return this.next;
    }

    /**
       * getLength
       * @return Returns the length of pagenation indices, -1 if not set.
       */
    public int getLength() {
        if (this.length < 1) {
            return -1;
        }
        return this.length;
    }

    /**
       * makeURLs
       * @param	  itemsSize The number of all items in total. 
       * @param	  itemsLength The number of all items in one page.
       * @param	  indicesLength The number of indices to be displayed.
       * @param	  index The current page index, starts at one.
       * @return Returns a list of URLs containing all possible pagination. 
       */
    public List<String> makeURLs(int itemsSize, int itemsLength, int indicesLength, int index) {
        if (itemsSize < 1 || itemsLength < 1 || indicesLength < 1 || index < 1) {
            return null;
        }
        
        init();
        
        int indicesSize = (int)Math.ceil(itemsSize / (double)itemsLength);
        this.current = index;
        this.length = indicesSize;
        
        List<String> pageLinks = genSeq(itemsSize, itemsLength, indicesLength, index, indicesSize)
                .stream().map(Object::toString)
                .map(ind -> this.next + ind)
                .collect(Collectors.toList());
        
        this.next += nextNum;
        return pageLinks;
    }
    
    /**
       * makeIndices
       * @param	  itemsSize
       * @param	  itemsLength
       * @param	  indicesLength
       * @param	  index
       * @return Returns a list of index numbers with all possible pagination. 
       */
    public List<Integer> makeIndices(int itemsSize, int itemsLength, int indicesLength, int index) {
        if (itemsSize < 1 || itemsLength < 1 || indicesLength < 1 || index < 1) {
            return null;
        }
        
        init();
        
        int indicesSize = (int)Math.ceil(itemsSize / (double)itemsLength);
        this.current = index;
        this.length = indicesSize;
        
        List<Integer> indices = genSeq(itemsSize, itemsLength, indicesLength, index, indicesSize);
        
        this.next += nextNum;
        return indices;
    }
    
    private List<Integer> genSeq(int itemsSize, int itemsLength, int indicesLength, int index, int indicesSize) {
        List<Integer> indices = new ArrayList<>();
        if ( indicesSize <= indicesLength ) {
            for (int i = 1; i <= indicesSize; i++) {
                indices.add(i);
            }
            this.nextNum = index == indicesSize ? -1 : index + 1;
            return indices;
        }
        
        int left = indicesLength/2;
        int right = indicesLength/2 - 1 + indicesLength%2;

        if ( index - left <= 0 ) {
            for (int i = 1; i <= indicesLength; i++) {
                indices.add(i);
            }
            this.nextNum = index + 1;
            return indices;
        }
        
        if ( index + right > indicesSize ) {
            for (int i = indicesSize - indicesLength + 1; i <= indicesSize; i++) {
                indices.add(i);
            }
            this.nextNum = index + 1 > indicesSize ? -1 : index + 1;
            return indices;
        }
        
        for (int i = index - left; i <= index + right; i++) {
            indices.add(i);
        }
        this.nextNum = index + 1;
        return indices;
    }
}
