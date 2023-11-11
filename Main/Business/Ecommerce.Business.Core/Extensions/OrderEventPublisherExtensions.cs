﻿using Ecommerce.Business.Core.Events.Checkout.Orders;
using Ecommerce.Business.Core.Utilities.Checkout;
using Ecommerce.Domain.Payments;
using MediatR;

namespace Ecommerce.Business.Core.Extensions
{
    public static class OrderEventPublisherExtensions
    {
        public static async Task PlaceOrderDetailsEvent<R, O>(this IMediator eventPublisher, R result, O order) where R : PlaceOrderResult where O : PlaceOrderContainer
        {
            await eventPublisher.Publish(new PlaceOrderDetailsEvent<R, O>(result, order));
        }
        public static async Task CapturePaymentTransactionDetailsEvent<R, C>(this IMediator eventPublisher, R result, C request) where R : CapturePaymentResult where C : PaymentTransaction
        {
            await eventPublisher.Publish(new CapturePaymentTransactionDetailsEvent<R, C>(result, request));
        }
        public static async Task VoidPaymentTransactionDetailsEvent<R, C>(this IMediator eventPublisher, R result, C request) where R : VoidPaymentResult where C : PaymentTransaction
        {
            await eventPublisher.Publish(new VoidPaymentTransactionDetailsEvent<R, C>(result, request));
        }
    }
}