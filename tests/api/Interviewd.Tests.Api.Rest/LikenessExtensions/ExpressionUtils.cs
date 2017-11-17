using System;
using System.Linq.Expressions;

namespace Interviewd.Tests.Api.Rest.LikenessExtensions
{
    internal class ExpressionUtils
    {
        internal static TProperty GetValue<TType, TProperty>(Expression<Func<TType, TProperty>> propertyPicker, TType obj)
        {
            return propertyPicker.Compile().Invoke(obj);
        }
    }
}