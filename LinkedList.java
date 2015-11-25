public class LinkedList implements List  {

    private ListItem head;
    private ListItem tail;
	private boolean sorted = true;

    public LinkedList() {
		
		new Thread(new ListWatcher()).start();
		System.out.println("Finished initialization");
    }

    public boolean isEmpty() {

        return head == null;
    }

    
    public int size() {

        int indexCount = 0;
        ListItem item = head;
        while (item != null) {
            indexCount++;
            item = item.next;
        }
        return indexCount;
    }

    
    public ReturnObject get(int index) {

        if (head == null) {
            return new ReturnObjectImpl(null, ErrorMessage.EMPTY_STRUCTURE);
        }
        if (isOutOfBounds(index)) {
            return new ReturnObjectImpl(null, ErrorMessage.INDEX_OUT_OF_BOUNDS);
        }
        if (index == 0) {
            return new ReturnObjectImpl(head.value, ErrorMessage.NO_ERROR);
        }
        ListItem listItemAtIndex = getListItemAtIndex(index);
        return new ReturnObjectImpl(listItemAtIndex.value, ErrorMessage.NO_ERROR);
    }

    
    public ReturnObject remove(int index) {

        if (head == null) {
            return new ReturnObjectImpl(null, ErrorMessage.EMPTY_STRUCTURE);
        }
        if (isOutOfBounds(index)) {
            return new ReturnObjectImpl(null, ErrorMessage.INDEX_OUT_OF_BOUNDS);
        }

        ListItem listItemAtIndex = getListItemAtIndex(index);
        if (listItemAtIndex.prev != null) {
            listItemAtIndex.prev.next = listItemAtIndex.next;
        }
        if (listItemAtIndex.next != null) {
            listItemAtIndex.next.prev = listItemAtIndex.prev;
        }
        if (listItemAtIndex == head) {
            head = head.next;
        } else if (listItemAtIndex == tail) {
            tail = tail.prev;
        }
        if (head == null) {
            tail = null;
        }
        return new ReturnObjectImpl(listItemAtIndex.value, ErrorMessage.NO_ERROR);
    }

    
    public ReturnObject add(int index, Integer item) {

        if (item == null) {
            return new ReturnObjectImpl(null, ErrorMessage.INVALID_ARGUMENT);
        }
        if (index != 0 && isOutOfBounds(index)) {
            return new ReturnObjectImpl(null, ErrorMessage.INDEX_OUT_OF_BOUNDS);
        }
        ListItem addItem = new ListItem(item);
        if (head == null) {
            head = addItem;
            tail = head;
        } else {
            ListItem listItemAtIndex = getListItemAtIndex(index);
            addItem.next = listItemAtIndex;
            addItem.prev = listItemAtIndex.prev;
            listItemAtIndex.prev = addItem;
            if (addItem.prev == null) {
                head = addItem;
            } else {
                addItem.prev.next = addItem;
            }
        }
        return new ReturnObjectImpl(null, ErrorMessage.NO_ERROR);
    }

    
    public ReturnObject add(Integer item) {

        if (item == null) {
            return new ReturnObjectImpl(null, ErrorMessage.INVALID_ARGUMENT);
        }
        ListItem addItem = new ListItem(item);
        if (head == null) {
            head = addItem;
            tail = head;
			sorted = true;
        } else {
            tail.next = addItem;
            addItem.prev = tail;
            tail = addItem;
			sorted = false;
        }
        return new ReturnObjectImpl(null, ErrorMessage.NO_ERROR);
    }

    private ListItem getListItemAtIndex(int index) {

        int indexCount = 0;
        ListItem item = head;
        while (item != null) {
            if (indexCount == index) {
                return item;
            }
            item = item.next;
            indexCount++;
        }
        return null;
    }

    private boolean isOutOfBounds(int index) {

        return index < 0 || (index > size() - 1);
    }

    private class ListItem {

        private final Integer value;
        //
        private ListItem prev;
        private ListItem next;

        private ListItem(Integer value) {
            this.value = value;
        }
    }
	
	class ListWatcher implements Runnable {
		
		private final int SLEEP = 500;
		private ListItem sortPos;
		public void run() {

		
			while(true) {				
				//
				//	If not sorted then incrementally sort
				//
				if (!sorted) {
					if (sortPos == null) {
						sortPos = head;
					}
					System.out.println("Not sorted: Sort position =  " + (sortPos == null ? "null" : sortPos.value)) ;
					ListItem first = sortPos;
					ListItem second = sortPos.next;
					if (shouldBeSwapped(first, second)) {
						swap(first, second);
					}
					while(second.prev != null) {
						first = second.prev;
						second = second;
						if (shouldBeSwapped(first, second)) {
							swap(first, second);
						}
					}
				}

				try {
					Thread.sleep(SLEEP);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}				
			}
		}
		
		private void swap(ListItem li1, ListItem li2 ) {
			
			System.out.println("Swap " + (li1 == null ? "null" : li1.value) + " with " + (li2 == null ? "null" : li2.value));

			li2.prev = li1.prev;
			li1.next = li2.next;
			li2.next = li1;				
			li1.prev = li2;		
			if (li1 == head) {
				head = li2;
			}
		}		
		private boolean shouldBeSwapped(ListItem li1, ListItem li2) {
			
			if (li1 == null || li2==null ) {
				return false;
			}
			return li1.value.compareTo(li2.value) > 0;
		}
	}
}