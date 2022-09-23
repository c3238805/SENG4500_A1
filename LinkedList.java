
/*  
    /============================\
    |  SENG4500 Assignment 1     | 
    |  Name : Ni Zeng            | 
    |  Student Number : c3238805 |
    \============================/   */
    
import java.util.ConcurrentModificationException;
import java.util.Iterator;

public class LinkedList<T extends TaxNode> implements Iterable<T> {
  private int modCount; // count
  private Node<T> sentinel;
  private int size;

  public Iterator<T> iterator() {
    return new thisiterator();
  }

  public LinkedList() {
    sentinel = new Node<T>();
    size = 0;
    modCount = 0;
  }

  // get the size of the list
  public int getSize() {
    return size;
  }

  public void add(T new_data) {

    if (size == 0) {
      append(new_data);
      return;
    }

    Node<T> x = new Node<T>(new_data); // allocate a new element
    Node<T> current = sentinel;
    while (current.getNext() != null) {
      current = current.getNext();
    }
    // Splice in the new element.
    x.setNext(current.getNext());
    x.setPrev(current);

    current.setNext(x);

    size++;
  }

  public void append(T o) {

    Node<T> add = new Node<T>(o);
    Node<T> n = sentinel;
    while (n.getNext() != null) {
      n = n.getNext();

    }
    n.setNext(add);
    add.setPrev(n);
    size++;
    modCount++; // incread modCount
  }

  public void remove(T c) {
    Node<T> current_taxNode = new Node<T>(c); // insert current_taxNode Node <T>

    Node<T> current = sentinel; // set current the sentinel node

    while (current.getNext() != null) {
      current = current.getNext();
      if (current.getData() == current_taxNode.getData()) {
        break; // stop the loop if reached the current_taxNode
      }
    }

    // add link to the node together .
    if (current.getNext() != null && current.getPrev() != null) {
      current.getPrev().setNext(current.getNext());

      current.getNext().setPrev(current.getPrev());
      ;
    } else if (current.getNext() == null && current.getPrev() != null) {
      current.getPrev().setNext(current.getNext());
    } else if (current.getNext() != null && current.getPrev() == null) {
      current.getNext().setPrev(current.getPrev());
    }

    size--;
    modCount++;
  }

  public void insertBefore(T before, T obj) {
    Node<T> b = new Node<T>(before); // insert before Node <T> before

    Node<T> n = new Node<T>(obj); // create a new node for input data
    Node<T> current = sentinel; // set current the sentinel node
    while (current.getNext() != null) {
      current = current.getNext();
      if (current.getData() == b.getData()) {
        break; // stop the loop if reached the before node.
      }
    }
    // add link to the node together .
    n.setPrev(current.getPrev());
    current.getPrev().setNext(n);
    n.setNext(current);
    current.setPrev(n);

    size++;
    modCount++;
  }

  public void insertAfter(T after, T obj) {
    Node<T> af = new Node<T>(after); // insert after Node <T> after

    Node<T> n = new Node<T>(obj); // create a new node for input data
    Node<T> current = sentinel; // set current the sentinel node

    while (current.getNext() != null) {
      current = current.getNext();
      if (current.getData() == af.getData()) {
        break; // stop the loop if reached the after node.
      }
    }
    if (current.getNext() != null) {
      // add link to the node together .
      current.getNext().setPrev(n);
      n.setNext(current.getNext());
      n.setPrev(current);
      current.setNext(n);
    } else {
      // add link to the node together .

      n.setNext(current.getNext());
      n.setPrev(current);
      current.setNext(n);
    }

    size++;
    modCount++;
  }

  // ----------------------------------------------------------
  public void insertInOrder(T new_data) {
    Node<T> current = sentinel; // set current to the last node of the list
    TaxNode emptyNode = new TaxNode(0, 0, 0, 0);
    if (size == 0) {
      append(new_data);
      return;
    } else if (size == 1 && current.getNext().getData().getStart_income().equals(0) &&
        current.getNext().getData().getEnd_income().equals(0)) {
      // when there is only one initialed TaxNode in the List
      current = current.getNext();
      if (new_data.getStart_income() == 0) {
        // update range
        current.getData().setEnd_income(new_data.getEnd_income());
        current.getData().setBase_tax(new_data.getBase_tax());
        current.getData().setTax_per_dollar(new_data.getTax_per_dollar());
      } else {
        // update range (only update the first TaxNode's end_income)
        current.getData().setEnd_income(new_data.getStart_income() - 1);
        // add new TaxNode into List
        add(new_data);
      }

    } else {

      while (current.getNext() != null) { // loop form the node after sentinel
        current = current.getNext();

        // compare the start_income (current vs. new_data)

        if (current.getData().compareTo(new_data) == 1) {
          // check if new_data's start_income < current.getNext(). start_income
          if ((current.getNext() == null)) {
            add(new_data); // when there is no next TaxNode in List.
            break;
          } else if ((current.getNext() != null)
              && new_data.getStart_income() < current.getNext().getData().getStart_income()) {

            insertBefore(current.getNext().getData(), new_data);

            // overwrite the data from current.next()
            while (current.getNext() != null) {

              current = current.getNext(); // first move cursor point to new_data

              if ((current.getNext() != null) && (current.getData().getEnd_income() != -1) &&
                  (current.getData().getEnd_income() >= current.getNext().getData().getStart_income())) {

                // compare current.end_income with current.next().end_income
                if (current.getData().getEnd_income() >= current.getNext().getData().getEnd_income()) {
                  // delete current.next()
                  remove(current.getNext().getData());

                } else { // when end_income fall in between current.next()'s range
                  // update current.next()'s start_income
                  current.getNext().getData().setStart_income(current.getData().getEnd_income() + 1);
                }

              } else if (current.getData().getEnd_income() == -1) {
                // delete all records after current
                while (current.getNext() != null) {
                  current = current.getNext();
                  // delete current.next()
                  remove(current.getData());

                }

              }

            }
            break; // when finish overwrite the Tax Scale , break

          }

        } // when start_income fall into an income rage
        else if (current.getData().compareTo(new_data) == -1) {

          // check the new_data end_income compare to current.end_income
          if (current.getData().getEnd_income() > new_data.getEnd_income()) {
            // create a new TaxNode to fill the gap

            T data = (T) new TaxNode();
            data.setStart_income(new_data.getEnd_income() + 1);
            data.setEnd_income(current.getData().getEnd_income());
            data.setBase_tax(current.getData().getBase_tax());
            data.setTax_per_dollar(current.getData().getTax_per_dollar());

            // insert new_data after current
            insertAfter(current.getData(), new_data);
            // update current's end_income
            current.getData().setEnd_income(current.getNext().getData().getStart_income() - 1);
            // insert new_data after current
            insertAfter(current.getNext().getData(), data);
            break;

          } else if (current.getData().getEnd_income().equals(new_data.getEnd_income())) {
            insertAfter(current.getNext().getData(), new_data);
            current.getData().setEnd_income(new_data.getStart_income() - 1);
          }

          else {

            // insert new_data after current
            insertAfter(current.getData(), new_data);
            // update current
            current.getData().setEnd_income(new_data.getStart_income() - 1);

            // check next TaxNode's range
            while (current.getNext() != null) {
              current = current.getNext();
              // when there is no next TaxNode in the list
              if(current.getNext() == null){
                  if((current.getData().getStart_income() <= current.getPrev().getData().getEnd_income())){
                    current.getData().setStart_income(current.getPrev().getData().getEnd_income() + 1);
                  }
              }
              else if ((current.getNext().getData().getStart_income() <= current.getData().getEnd_income())
                  && (current.getNext().getData().getEnd_income() > current.getData().getEnd_income())) {

                current.getNext().getData().setStart_income(current.getData().getEnd_income() + 1);

              } else if (current.getNext().getData().getEnd_income() <= current.getData().getEnd_income()) {
                // clean the TaxNode
                remove(current.getNext().getData());

              }

            }

            break; // when finish overwrite the Tax Scale , break

          }
        }

        else if (current.getData().compareTo(new_data) == 2) {
          // compare the end_income
          if (current.getData().getEnd_income() > new_data.getEnd_income()) {
            current.getData().setStart_income(new_data.getEnd_income() + 1);
            insertBefore(current.getData(), new_data);

          } else if (current.getData().getEnd_income() <= new_data.getEnd_income()) {
            // update the current TaxNode
            current.getData().setEnd_income(new_data.getEnd_income());
            current.getData().setBase_tax(new_data.getBase_tax());
            current.getData().setTax_per_dollar(new_data.getTax_per_dollar());

            // check next TaxNode's range
            while (current.getNext() != null) {
              if (current.getNext().getData().getEnd_income() <= current.getData().getEnd_income()) {
                // clean the TaxNode
                remove(current.getNext().getData());

              } else if ((current.getNext().getData().getStart_income() <= current.getData().getEnd_income())
                  && (current
                      .getNext().getData().getEnd_income() > current.getData().getEnd_income())) {

                current.getNext().getData().setStart_income(current.getData().getEnd_income() + 1);
              }

              current = current.getNext();

              if (current == null) {
                break;
              }

            }

            break;

          }

        }

        else {

          if ((current.getNext() == null)) {
            // if current has a un defined end income
            if (current.getData().getEnd_income().equals(-1)) {

              if (!new_data.getEnd_income().equals(-1)) {
                // create an end TaxNode and insert after the new_data
                T data = (T) new TaxNode();
                data.setStart_income(new_data.getEnd_income() + 1);
                data.setEnd_income(-1);
                data.setBase_tax(current.getData().getBase_tax());
                data.setTax_per_dollar(current.getData().getTax_per_dollar());

                // update current end_income
                current.getData().setEnd_income(new_data.getStart_income() - 1);
                add(new_data); // when there is no next TaxNode in List.

                // insert into List
                insertAfter(new_data, data);
              } else {
                current.getData().setEnd_income(new_data.getStart_income() - 1);
                add(new_data); // when there is no next TaxNode in List.
              }

            } else {
              current.getData().setEnd_income(new_data.getStart_income() - 1);
              add(new_data); // when there is no next TaxNode in List.
            }

            break;

          } else if ((current.getNext() != null)
              && new_data.getStart_income() < current.getNext().getData().getStart_income()) {

            //
            if ((current.getData().getEnd_income() == -1) && (new_data.getEnd_income() != -1)) {
              current.getData().setEnd_income(new_data.getStart_income() - 1);

              insertAfter(current.getData(), new_data);

              // add a TaxNode after new_data.
              T data = (T) new TaxNode();
              data.setStart_income(new_data.getEnd_income() + 1);
              data.setEnd_income(-1);
              data.setBase_tax(current.getData().getBase_tax());
              data.setTax_per_dollar(current.getData().getTax_per_dollar());

              // insert into List
              insertAfter(new_data, data);

              while (current.getNext() != null) {

                current = current.getNext(); // first move cursor point to new_data
                // delete all TaxNode after new_data
                remove(current.getData());

              }

            } else if ((new_data.getEnd_income() == -1) && (current.getData().getEnd_income() != -1)) {

              if (current.getData().getStart_income().equals(new_data.getStart_income())) {
                // replace current TaxNode with new_data
                current.getData().setEnd_income(-1);
                current.getData().setBase_tax(new_data.getBase_tax());
                current.getData().setTax_per_dollar(new_data.getTax_per_dollar());

                while (current.getNext() != null) {
                  current = current.getNext(); // first move cursor point to new_data
                  // delete all TaxNode after new_data
                  remove(current.getData());

                }

              } else {
                insertAfter(current.getData(), new_data);
                current.getData().setEnd_income(new_data.getStart_income() - 1);
                current = current.getNext(); // move cursor to new_data
                while (current.getNext() != null) {

                  current = current.getNext(); // first move cursor point to new_data
                  // delete all TaxNode after new_data
                  remove(current.getData());

                }
              }

            } else if ((new_data.getEnd_income() == -1) && (current.getData().getEnd_income() == -1)) {

              if (new_data.getStart_income() > current.getData().getStart_income()) {

                current.getData().setEnd_income(new_data.getStart_income() - 1);
                new_data.setEnd_income(-1);
                insertAfter(current.getData(), new_data);

                while (current.getNext() != null) {

                  current = current.getNext(); // first move cursor point to new_data
                  // delete all TaxNode after new_data
                  remove(current.getData());

                }

              } else if (new_data.getStart_income().equals(current.getData().getStart_income())) {

                // replace current TaxNode with new_data
                current.getData().setEnd_income(-1);
                current.getData().setBase_tax(new_data.getBase_tax());
                current.getData().setTax_per_dollar(new_data.getTax_per_dollar());

                while (current.getNext() != null) {

                  current = current.getNext(); // first move cursor point to new_data
                  // delete all TaxNode after new_data
                  remove(current.getData());
                }

              }

            }

          }

        }
      }

    }
  }

  // ===============================================
  private class thisiterator implements Iterator<T> {
    private int expectedModCount; // count

    private Node<T> current;

    // construtor
    private thisiterator() {
      this.current = sentinel;
      expectedModCount = modCount;
    }

    // search if the Iterator have next()
    public T next() {
      if (expectedModCount != modCount)
        throw new ConcurrentModificationException("Cannot mutate in context of iterator");

      this.current = current.getNext();
      return current.getData();
    }

    public T currentI() {
      if (expectedModCount != modCount)
        throw new ConcurrentModificationException("Cannot mutate in context of iterator");

      return this.current.getData();
    }

    // if getNext() == null, the iterator reach till the end of the list
    public boolean hasNext() {
      return this.current.getNext() != null; // true
    }

  }

}