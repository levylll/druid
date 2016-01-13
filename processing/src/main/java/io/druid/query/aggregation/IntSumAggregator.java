/*
 * Licensed to Metamarkets Group Inc. (Metamarkets) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. Metamarkets licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package io.druid.query.aggregation;

import com.google.common.collect.Ordering;
import com.google.common.primitives.Ints;
import io.druid.segment.IntColumnSelector;

import java.util.Comparator;

/**
 */
public class IntSumAggregator implements Aggregator
{
  static final Comparator COMPARATOR = new Ordering()
  {
    @Override
    public int compare(Object o, Object o1)
    {
      return Ints.compare(((Number) o).intValue(), ((Number) o1).intValue());
    }
  }.nullsFirst();

  static int combineValues(Object lhs, Object rhs)
  {
    return ((Number) lhs).intValue() + ((Number) rhs).intValue();
  }

  private final IntColumnSelector selector;
  private final String name;

  private int sum;

  public IntSumAggregator(String name, IntColumnSelector selector)
  {
    this.name = name;
    this.selector = selector;

    this.sum = 0;
  }

  @Override
  public void aggregate()
  {
    sum += selector.get();
  }

  @Override
  public void reset()
  {
    sum = 0;
  }

  @Override
  public Object get()
  {
    return sum;
  }

  @Override
  public float getFloat()
  {
    return (float) sum;
  }

  @Override
  public long getLong()
  {
    return (long) sum;
  }

  @Override
  public int getInt()
  {
    return sum;
  }

  @Override
  public double getDouble()
  {
    return (double) sum;
  }

  @Override
  public String getName()
  {
    return this.name;
  }

  @Override
  public Aggregator clone()
  {
    return new IntSumAggregator(name, selector);
  }

  @Override
  public void close()
  {
    // no resources to cleanup
  }
}